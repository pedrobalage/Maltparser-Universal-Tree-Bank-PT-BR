#!/usr/bin/python
# -*- coding: utf-8 -*-

#### Script to execute the mxpost and malparser to annotate a sentence sent from the input or by file
####
#### Some code borrow from http://www.nltk.org/_modules/nltk/parse/malt.html

# Author: Pedro Balage (pedrobalage@gmail.com)
# Date: 25/05/2015
# Version: 1.0

# Python 3 compatibility
from __future__ import unicode_literals
from __future__ import division
from __future__ import absolute_import
from __future__ import with_statement
from __future__ import print_function

# imports
from subprocess import Popen, PIPE
import tempfile
import fileinput
import codecs
import os
import sys


# read from Stdin or from files 
sentences = []
for line in fileinput.input(openhook = fileinput.hook_encoded('utf-8')):
    # MXPOST 
    p = Popen(['java', 
                '-mx30m', 
                '-cp', 
                'mxpost/mxpost.jar',
                'tagger.TestTagger',
                'pt-br-universal-tagger.project'], 
            stdin = PIPE, stdout=PIPE, stderr=PIPE)

    stdout, stderr = p.communicate(input=line)
    sentence = stdout.decode('utf8')
    tokens = [tuple(w.split('_')) for w in sentence.split()]
    sentences.append(tokens)
    

# MALT Parser
input_file = tempfile.NamedTemporaryFile(prefix='malt_input.conll',
                                                dir=tempfile.gettempdir(),
                                                delete=False)
output_file = tempfile.NamedTemporaryFile(prefix='malt_output.conll',
                                                dir=tempfile.gettempdir(),
                                                delete=False)

try:
    for sentence in sentences:
        for (i, (word, tag)) in enumerate(sentence, start=1):
            input_str = '%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n' %\
                (i, word, '_', tag, tag, '_', '0', 'a', '_', '_')
            input_file.write(input_str.encode("utf8"))
        input_file.write(b'\n\n')
    input_file.close()

    cmd = ['java' ,
            '-jar', 'maltparser-1.8.1/maltparser-1.8.1.jar',
            '-c'  , 'uni-dep-tb-ptbr', 
            '-i'  , input_file.name,
            '-o'  , output_file.name, 
            '-m'  , 'parse']

    p = Popen(cmd, stdout=PIPE, stderr=PIPE)
    ret = p.wait()

    print(codecs.open(output_file.name,'r',encoding='utf8').read())

finally:
    input_file.close()
    os.remove(input_file.name)
    output_file.close()
    os.remove(output_file.name)
