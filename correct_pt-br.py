#!/usr/bin/python
# -*- coding: utf-8 -*-

#### Script to correct problems reported in the pt-br version of the Universal Dependency TreeBank.
#### This script corrects the 'ea' problem, where 'e' is a CONJ and 'a' is a feminine DET. The problem also happens with 'eo' for masculine DET 'o'
#### The correction splits 'e' and 'a' into two tokens. Due the creation of a new token is necessary to redefine the Ids and the Heads
####
#### Corpus reference:
####
#### Universal Dependency Treebank
#### https://code.google.com/p/uni-dep-tb

# Author: Pedro Balage (pedrobalage@gmail.com)
# Date: 29/04/2015
# Version: 1.0

# Python 3 compatibility
from __future__ import unicode_literals
from __future__ import division
from __future__ import absolute_import
from __future__ import print_function
from __future__ import with_statement

# imports
import os
import codecs

CORPUS_PATH = 'universal_treebanks_v2.0/std/pt-br/'
NEW_CORPUS_PATH = 'pt-br-corrected/'

if not os.path.isdir('pt-br-corrected'):
    os.mkdir('pt-br-corrected')


# if a grid has the 'ea' problem in problem_index, corrects the tokens ids and
# the dependency heads with the new tokens created.
# returns a new grid with the problem corrected
def correct_grid(grid, problem_index):

    new_grid = list()
    for line_items in grid:

        # identify the 'ea' / 'eo' token to split
        if int(line_items[0]) == problem_index:
            next_token = ''
            if line_items[1] == 'ea':
                next_token = 'a'
            if line_items[1] == 'eo':
                next_token = 'o'

            line_items[1] = 'e'
            new_grid.append(line_items)

            # The DET in the great majority of the cases
            # have head to the next token, usually a
            # noun. In constituent grammars a 'DET + NOUN'
            # makes a 'SN'. We evidenced this problem
            # occurring only when 'ea' is on 'CONJ' + 'NOUN'
            new_line_items = [str(problem_index+1), next_token, '_', 'DET', 'DET', '_', str(problem_index+2), 'det', '_', '_' ]
            new_grid.append(new_line_items)

        else:

            # if the token is located after where the problem
            # happend, adds 1 to the tokens id due the
            # creation of a new token
            if int(line_items[0]) > problem_index:
                line_items[0] = str( int(line_items[0]) + 1 )

            # if the dependency head is located after where the problem
            # happend, adds 1 to the head id due the
            # creation of a new token
            if int(line_items[6]) > problem_index:
                line_items[6] = str( int(line_items[6]) + 1 )

            new_grid.append(line_items)

    return new_grid


for filename in os.listdir(CORPUS_PATH):
    with codecs.open(CORPUS_PATH + filename,'r',encoding='utf8') as input_file:
        with codecs.open(NEW_CORPUS_PATH + filename, 'w', encoding='utf8') as output_file:

            grid = list()
            problem_index = 0
            any_problem = False

            for line in input_file:
                line = line.strip()

                # blank line. Sentence boundary.
                # It writes the grid structure to
                # the output_file
                if len(line) == 0:
                    if any_problem:
                        grid = correct_grid(grid,problem_index)
                    for line_items in grid:
                        output_file.write( '\t'.join(line_items) + '\n')

                    output_file.write('\n')

                    any_problem = False
                    grid = list()
                    continue

                line_items = line.split('\t')

                if len(line_items) != 10:
                    print ('Problem, line doesnt have 10 values: \n{0}\n\n'.format(line))

                grid.append(line_items)

                # identify 'ea' problem
                if line_items[1] == 'ea':
                    any_problem = True
                    # The problem happens only once per sentence,
                    # so this simple problem indexing is sufficient
                    # to correct the files
                    problem_index = int(line_items[0])

        # at the end of the file, save the last sentence
        if len(grid) != 0:
            if any_problem:
                grid = correct_grid(grid,problem_index)
            for line_items in grid:
                output_file.write( '\t'.join(line_items))
