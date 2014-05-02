package com.rts.server.attribute;

public class BuildEffort extends Attribute {

	public BuildEffort(long pBuildEffort) {
		super(AttributeType.BuildEffort, pBuildEffort);
	}

	public void setBuildEffort(long pBuildEffort) {
		setValue(pBuildEffort);
	}

	public long getBuildEffort() {
		return (long) getValue();
	}

}
