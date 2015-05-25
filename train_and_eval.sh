#! /bin/sh

# Train a new model
java -jar maltparser-1.8.1/maltparser-1.8.1.jar -c uni-dep-tb-ptbr -i pt-br-corrected/pt-br-universal-train.conll -m learn


# Test the new model
java -jar maltparser-1.8.1/maltparser-1.8.1.jar -c uni-dep-tb-ptbr -i pt-br-corrected/pt-br-universal-test.conll -o malt_test.conll -m parse
java -jar malteval/lib/MaltEval.jar -s malt_test.conll -g pt-br-corrected/pt-br-universal-test.conll
