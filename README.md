## MXPOST and Maltparser trained with the Universal Dependency Treebank for Brazilian-Portuguese Language

This repository contains the code necessary to train the MXPost and Malt dependency parser with the Universal Dependency Treebank for the Brazilian-Portuguese

MXPOST
[http://www.inf.ed.ac.uk/resources/nlp/local\_doc/MXPOST.html](http://www.inf.ed.ac.uk/resources/nlp/local\_doc/MXPOST.html)

Malt Parser
[http://www.maltparser.org/](http://www.maltparser.org/)

Malt Eval
[http://www.maltparser.org/malteval.html](http://www.maltparser.org/malteval.html)

Universal Dependency Treebank
[https://code.google.com/p/uni-dep-tb](https://code.google.com/p/uni-dep-tb)

In order to use the tagger you should run the following command:

`echo "Esta sentença é de teste" | java -mx30m -cp mxpost/mxpost.jar tagger.TestTagger pt-br-universal-tagger.project`

In order to use the parser you should run the following java command:

`java -jar maltparser-1.8.1/maltparser-1.8.1.jar -c uni-dep-tb-ptbr -i test.conll -o output.conll -m parse`

where test.conll is your input file. Please, verify the test.conll file format for your input.

The script train\_and\_eval.sh provide the commands to train a new model and to evalute. 

The evaluation on pt-br-corrected/pt-br-universal-test.conll scored 0.83 of accuracy.

### Execution

I created an integrated solution to process a sentence and run mxpost and maltparser in a single command line.

In order to use this feature you may run sending the text by the stdin:

`echo "Uma palavra ou vocábulo é uma unidade da linguagem falada ou escrita ." | ./parse.py`

or 

`cat myfile.txt | ./parse.py`

The script does not tokenize the text, thus you should provide the input already tokenized.

### Issues

I am working in a python3 compatible code.

### Autor
Pedro Paulo Balage - [http://pedrobalage.com](http://pedrobalage.com)
