## Maltparser trained with the Universal Dependency Treebank for Brazilian-Portuguese Language

This repository contains the code necessary to train the Malt dependency parser with the Universal Dependency Treebank for the Brazilian-Portuguese

Malt Parser reference:
[http://www.maltparser.org/](http://www.maltparser.org/)

Malt Eval
[http://www.maltparser.org/malteval.html](http://www.maltparser.org/malteval.html)

Universal Dependency Treebank
[https://code.google.com/p/uni-dep-tb](https://code.google.com/p/uni-dep-tb)

In order to use the parser you should run the following java command:

```java -jar maltparser-1.8.1/maltparser-1.8.1.jar -c uni-dep-tb-ptbr -i test.conll -o output.conll -m parse```

where test.conll is your input file. Please, verify the test.conll file format for your input.

The script train\_and\_eval.sh provide the commands to train a new model and to evalute. 

The evaluation on pt-br-corrected/pt-br-universal-test.conll scored 0.83 of accuracy.

## Autor
Pedro Paulo Balage - [http://pedrobalage.com](http://pedrobalage.com)
