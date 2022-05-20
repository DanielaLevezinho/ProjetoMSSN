package fractais;

public class LSystem {

	private String sequence;
	private Rule[] ruleset;
	private int generation;
	private int id;

	public LSystem(String axiom, Rule[] ruleset, int id) {
		this.id = id;
		sequence = axiom;
		this.ruleset = ruleset;
		generation = 0;
	}

	public int getId() {
		return id;
	}
	public int getGeneration() {
		return generation;
	}

	public String getSequence() {
		return sequence;
	}

	public void nextGeneration() {
		generation++;

		String nextgen = "";

		for (int i = 0; i < sequence.length(); i++) {
			char c = sequence.charAt(i);
			String replace = "" + c;
			for (int j = 0; j < ruleset.length; j++) {
				if (c == ruleset[j].getSymbol()) {
					replace = ruleset[j].getString();
					break;
				}
			}

			nextgen += replace;
		}
		this.sequence = nextgen;
	}

}
