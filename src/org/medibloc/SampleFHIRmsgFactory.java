package org.medibloc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Condition;
import org.hl7.fhir.r4.model.Duration;
import org.hl7.fhir.r4.model.Extension;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.MedicationRequest;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.Signature;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.r4.model.Type;
import org.hl7.fhir.r4.model.Bundle.BundleType;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.r4.model.Identifier.IdentifierUse;
import org.hl7.fhir.r4.model.MedicationRequest.MedicationRequestDispenseRequestComponent;
import org.hl7.fhir.r4.model.Narrative;
import org.hl7.fhir.r4.model.Observation;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.primitive.IdDt;

public class SampleFHIRmsgFactory {

	public Bundle getYonseiSampleFHIRMsg() {
		Bundle bundle = new Bundle();
		bundle.setType(BundleType.DOCUMENT);
		
		Signature sign = new Signature();
		Date date = new Date(java.lang.System.currentTimeMillis());
		bundle.setSignature(sign.setWhen(date));
		
		Reference signedProvider = new Reference();
		signedProvider.setReference("Arim Min");
		bundle.setSignature(sign.setWho(signedProvider));

		// Create a patient object
		Patient patient = new Patient();
		patient.addIdentifier().setUse(IdentifierUse.OFFICIAL).setSystem("urn:example").setValue("7000135");
		// Give the patient a temporary UUID so that other resources in
		patient.setId(IdDt.newRandomUuid());
		patient.addName(new HumanName().setFamily("Test"));
		patient.setGender(AdministrativeGender.MALE);

		bundle.addEntry().setResource(patient);
		
		// Create an Severity extension
		Extension e = new Extension();
		e.setUrl("http://medibloc.org/extensions#Severity").setValue(new StringType("1"));

		// Add the extension to the resource
		patient.addExtension(e);
		
		// Create a Condition object
		Condition cond = new Condition();
		CodeableConcept cc = new CodeableConcept();
		List<Coding> theCoding = new ArrayList<Coding>();		
		theCoding.add(new Coding().setCode("None").setDisplay("epi pain +, moderate pain"));
		theCoding.add(new Coding().setCode("K291").setSystem("Korean Classification of Diseases").setDisplay("기타급성위염"));
		theCoding.add(new Coding().setCode("K30").setSystem("Korean Classification of Diseases").setDisplay("기능성 소화불량"));
		theCoding.add(new Coding().setCode("K528").setSystem("Korean Classification of Diseases").setDisplay("기타 명시된 비감염성 위장염 및 결장염"));
		cc.setCoding(theCoding);
		cond.setCode(cc);
		
		bundle.addEntry().setResource(cond);

		Observation obs1 = new Observation();
		obs1.addChild("Body Temperature");
		Narrative n1 = new Narrative();
		n1.addChild("36.7");
		obs1.setValue(n1);
		
		Observation obs2 = new Observation();
		obs2.addChild("Body Weght");
		Narrative n2 = new Narrative();
		n2.addChild("13");
		obs1.setValue(n2);

		bundle.addEntry().setResource(obs1);
		bundle.addEntry().setResource(obs2);
		
		List<Coding> md1 = new ArrayList<Coding>();
		md1.add(new Coding().setCode("epice").setDisplay("에피세람"));
		List<Coding> md2 = new ArrayList<Coding>();
		md2.add(new Coding().setCode("zcre").setDisplay("제로이드인텐시브크림"));
		
		bundle.addEntry().setResource(
				new MedicationRequest().setMedication(new CodeableConcept().setCoding(md1))
					.setDispenseRequest(new MedicationRequestDispenseRequestComponent().setQuantity(new Quantity().setValue(1)).setDispenseInterval((Duration) new Duration().setValue(1)))
			);
		bundle.addEntry().setResource(
				new MedicationRequest().setMedication(new CodeableConcept().setCoding(md2))
					.setDispenseRequest(new MedicationRequestDispenseRequestComponent().setQuantity(new Quantity().setValue(1)).setDispenseInterval((Duration) new Duration().setValue(1)))
			);
		
		return bundle;
	}

	public Bundle getMinSampleFHIRMsg() {
		Bundle bundle = new Bundle();
		bundle.setType(BundleType.DOCUMENT);
		
		Signature sign = new Signature();
		Date date = new Date(java.lang.System.currentTimeMillis());
		bundle.setSignature(sign.setWhen(date));
		
		Reference signedProvider = new Reference();
		signedProvider.setReference("Chang-ei Hong");
		
		bundle.setSignature(sign.setWho(signedProvider));

		// Create a patient object
		Patient patient = new Patient();
		patient.addIdentifier().setUse(IdentifierUse.OFFICIAL).setSystem("urn:example").setValue("7000135");
		// Give the patient a temporary UUID so that other resources in
		patient.setId(IdDt.newRandomUuid());
		patient.addName(new HumanName().setFamily("Test"));
		patient.setGender(AdministrativeGender.MALE);

		bundle.addEntry().setResource(patient);
		
		// Create an Severity extension
		Extension e = new Extension();
		e.setUrl("http://medibloc.org/extensions#Severity").setValue(new StringType("1"));

		// Add the extension to the resource
		patient.addExtension(e);
		
		// Create a Condition object
		Condition cond = new Condition();
		CodeableConcept cc = new CodeableConcept();
		List<Coding> theCoding = new ArrayList<Coding>();		
		theCoding.add(new Coding().setCode("I209").setSystem("Korean Classification of Diseases").setDisplay("상세불명의 아토피성 피부염"));
		theCoding.add(new Coding().setCode("None").setDisplay("가려워함 아이는 안움 제로이드 처방 원하심"));
		cc.setCoding(theCoding);
		cond.setCode(cc);
		
		bundle.addEntry().setResource(cond);

		List<Coding> md1 = new ArrayList<Coding>();
		md1.add(new Coding().setCode("HYBA").setDisplay("히스판주"));
		List<Coding> md2 = new ArrayList<Coding>();
		md2.add(new Coding().setCode("알비스타C").setDisplay("알비스타연질캡슐"));
		List<Coding> md3 = new ArrayList<Coding>();
		md3.add(new Coding().setCode("TRP").setDisplay("티로푸정(정)"));
		List<Coding> md4 = new ArrayList<Coding>();
		md4.add(new Coding().setCode("오티렌").setDisplay("오티렌F정"));
		List<Coding> md5 = new ArrayList<Coding>();
		md5.add(new Coding().setCode("가스베트정").setDisplay("가스베트정5밀리그램"));
		List<Coding> md6 = new ArrayList<Coding>();
		md6.add(new Coding().setCode("G-AMGF").setDisplay("알마겔에프현탁액"));
		
		bundle.addEntry().setResource(
				new MedicationRequest().setMedication(new CodeableConcept().setCoding(md1))
					.setDispenseRequest(new MedicationRequestDispenseRequestComponent().setQuantity(new Quantity().setValue(1)).setDispenseInterval((Duration) new Duration().setValue(1)))
			);
		bundle.addEntry().setResource(
				new MedicationRequest().setMedication(new CodeableConcept().setCoding(md2))
					.setDispenseRequest(new MedicationRequestDispenseRequestComponent().setQuantity(new Quantity().setValue(3)).setDispenseInterval((Duration) new Duration().setValue(3)))
			);
		bundle.addEntry().setResource(
				new MedicationRequest().setMedication(new CodeableConcept().setCoding(md3))
					.setDispenseRequest(new MedicationRequestDispenseRequestComponent().setQuantity(new Quantity().setValue(3)).setDispenseInterval((Duration) new Duration().setValue(3)))
			);
		bundle.addEntry().setResource(
				new MedicationRequest().setMedication(new CodeableConcept().setCoding(md4))
					.setDispenseRequest(new MedicationRequestDispenseRequestComponent().setQuantity(new Quantity().setValue(2)).setDispenseInterval((Duration) new Duration().setValue(3)))
			);
		bundle.addEntry().setResource(
				new MedicationRequest().setMedication(new CodeableConcept().setCoding(md5))
					.setDispenseRequest(new MedicationRequestDispenseRequestComponent().setQuantity(new Quantity().setValue(3)).setDispenseInterval((Duration) new Duration().setValue(3)))
			);
		bundle.addEntry().setResource(
				new MedicationRequest().setMedication(new CodeableConcept().setCoding(md6))
					.setDispenseRequest(new MedicationRequestDispenseRequestComponent().setQuantity(new Quantity().setValue(3)).setDispenseInterval((Duration) new Duration().setValue(3)))
			);
		
		return bundle;
	}
}
