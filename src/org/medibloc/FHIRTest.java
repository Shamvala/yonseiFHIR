package org.medibloc;

import org.hl7.fhir.r4.model.Bundle;

import ca.uhn.fhir.context.FhirContext;

public class FHIRTest {

	public static void main(String[] args) {

		Bundle minFHIRmsg = new SampleFHIRmsgFactory().getMinSampleFHIRMsg();

		Bundle younseiFHIRmsg = new SampleFHIRmsgFactory().getMinSampleFHIRMsg();

		// Log the request
		FhirContext ctx = FhirContext.forR4();
		System.out.println(ctx.newXmlParser().setPrettyPrint(true).encodeResourceToString(minFHIRmsg));

	}
}
