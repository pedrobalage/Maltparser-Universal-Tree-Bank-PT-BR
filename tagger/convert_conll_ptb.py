#!/usr/bin/python
# -*- coding: utf-8 -*-

#### Script to convert conll corpus to ptb format
####

# Author: Pedro Balage (pedrobalage@gmail.com)
# Date: 25/05/2015
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

CORPUS_FILE = '../pt-br-corrected/pt-br-universal-train-dev.conll'
TRAIN_FILE = 'pt-br-universal-train-dev.ptb' # to be created

with codecs.open(CORPUS_FILE,'r',encoding='utf8') as input_file:
    with codecs.open(TRAIN_FILE,'w',encoding='utf8') as output_file:

        grid = list()

        for line in input_file:
            line = line.strip()

            # blank line. Sentence boundary.
            # It writes the sentence in WORD_TAG structure in the output_file
            if len(line) == 0:
                output_file.write( ' '.join([line_items[1] + '_' + line_items[3] for line_items in grid]) + '\n')
                grid = list()
                continue

            line_items = line.split('\t')

            if len(line_items) != 10:
                print ('Problem, line doesnt have 10 values: \n{0}\n\n'.format(line))

            grid.append(line_items)

        # at the end of the file, save the last sentence
        if len(grid) != 0:
            output_file.write( ' '.join([line_items[1] + '' + line_items[3] for line_items in grid]) + '\n')
