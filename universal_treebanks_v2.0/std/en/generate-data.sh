# Set this to point to directory with wsj data from PTB v3.0.
# http://www.ldc.upenn.edu/Catalog/CatalogEntry.jsp?catalogId=LDC99T42
WSJDIR=treebank3/parsed/mrg/wsj/

javac -cp "commons-lang3-3.1.jar:." normalize.java

# Generate splits.
echo "creating train/dev/test splits ..."
cat $WSJDIR/0[2-9]/*.mrg $WSJDIR/1*/*.mrg \
  $WSJDIR/2[0-1]/*.mrg > train-tmp.mrg
cat $WSJDIR/22/*.mrg > dev-tmp.mrg
cat $WSJDIR/23/*.mrg > test-tmp.mrg

# Create stanford dependencies
splits=(test dev train)
for split in ${splits[@]}; do
  echo "create Stanford dependencies for split "$split" ..."
  java -cp stanford-parser-v1.6.8.jar \
    -Xmx2g \
    edu.stanford.nlp.trees.EnglishGrammaticalStructure \
    -treeFile $split-tmp.mrg \
    -conllx -basic -makeCopulaHead -keepPunct > $split-stanford-raw.conll

  echo "normalizing "$split" ..."
  java -cp "commons-lang3-3.1.jar:." \
    normalize $split-stanford-raw.conll ENGLISH-fine-to-universal.full.map \
    > en-univiersal-$split.conll

  rm -f $split-tmp.mrg $split-stanford-raw.conll
done

rm normalize.class
