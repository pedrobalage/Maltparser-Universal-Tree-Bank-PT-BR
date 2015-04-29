

import java.util.Vector;

import se.vxu.msi.malteval.MaltEvalConfig;
import se.vxu.msi.malteval.corpus.MaltSentence;
import se.vxu.msi.malteval.grouping.ArcDirection;
import se.vxu.msi.malteval.grouping.Deprel;
import se.vxu.msi.malteval.grouping.Grouping;
import se.vxu.msi.malteval.util.DataContainer;

public class ArcDirectionAndDeprel implements Grouping {
	private ArcDirection arcDirection;
	private Deprel deprel;

	public Object getKey(MaltSentence sentence, int wordIndex) {
		return arcDirection.getKey(sentence, wordIndex) + " / " + deprel.getKey(sentence, wordIndex);
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
