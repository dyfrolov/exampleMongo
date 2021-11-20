package mongoExample.entities;

public class TestCollection {
	private String testStringField;
	private int testIntField;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + testIntField;
		result = prime * result + ((testStringField == null) ? 0 : testStringField.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TestCollection))
			return false;
		TestCollection other = (TestCollection) obj;
		if (testIntField != other.testIntField)
			return false;
		if (testStringField == null) {
			if (other.testStringField != null)
				return false;
		} else if (!testStringField.equals(other.testStringField))
			return false;
		return true;
	}
	public String getTestStringField() {
		return testStringField;
	}
	public void setTestStringField(String testStringField) {
		this.testStringField = testStringField;
	}
	public int getTestIntField() {
		return testIntField;
	}
	public void setTestIntField(int testIntField) {
		this.testIntField = testIntField;
	}
}
