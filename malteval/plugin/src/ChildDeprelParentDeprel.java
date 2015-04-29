
import java.util.Vector;

import se.vxu.msi.malteval.MaltEvalConfig;
import se.vxu.msi.malteval.corpus.MaltSentence;
import se.vxu.msi.malteval.grouping.ArcDirection;
import se.vxu.msi.malteval.grouping.Deprel;
import se.vxu.msi.malteval.grouping.Grouping;
import se.vxu.msi.malteval.util.DataContainer;

public class ChildDeprelParentDeprel implements Grouping {
	private ArcDirection arcDirection;
	private Deprel deprel;

	public Object getKey(MaltSentence sentence, int wordIndex) {
		String key;
		String direction;
		if (sentence.getWord(wordIndex).getHead() == 0) {
			key = "<null> -R> " + sentence.getWord(wordIndex).getDeprel();
		} else {
			if (sentence.getWord(wordIndex).getHead() - 1 < wordIndex) {
				direction = " -R> ";
			} else {
				direction = " -L> ";
			}
			key = sentence.getWord(sentence.getWord(wordIndex).getHead() - 1).getDeprel() + direction + sentence.getWord(wordIndex).getDeprel();
		}
		return key;
	}

	public void initialize() {
		arcDirection = new ArcDirection();
		deprel = new Deprel();
		arcDirection.initialize();
		deprel.initialize();
	}

	public boolean isComplexGroupItem() {
		return false;
	}

	public DataContainer postProcess(DataContainer arg0) {
		return arg0;
	}

	public boolean showDetails() {
		return true;
	}

	public boolean showTableHeader() {
		return false;
	}

	public String correspondingMetric() {
		return null;
	}

	public String getSelfMetricName() {
		return getClass().getSimpleName();
	}

	public boolean isSimpleAccuracyGrouping() {
		return false;
	}

	public String getSecondName() {
		return null;
	}

	public boolean isCorrect(int wordIndex, MaltSentence goldSentence, MaltSentence parsedSentence) {
		return getKey(goldSentence, wordIndex).equals(getKey(parsedSentence, wordIndex));
	}
}
