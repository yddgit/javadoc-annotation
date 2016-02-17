package com.my.project.annotation;

@CustomAnnotationClass(author = "yang", date = "2015-07-18")
public class AnnotatedClassB extends AnnotatedClassA {

	@CustomAnnotationMethod(author = "friend of mine", date = "2014-06-05", description = "annotated method")
	public String annotatedMethodA() {
		return "nothing niente";
	}

	@CustomAnnotationMethod(date = "2014-06-05", description = "annotated method")
	public String annotatedMethodB() {
		return "nothing niente";
	}
}
