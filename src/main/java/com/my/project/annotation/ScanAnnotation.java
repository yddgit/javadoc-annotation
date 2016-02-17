package com.my.project.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ScanAnnotation {
	public static void main(String[] args) throws Exception {

		Class<AnnotatedClassB> object = AnnotatedClassB.class;

		Annotation[] annotations = object.getAnnotations();
		for (Annotation annotation : annotations) {
			System.out.println(annotation);
		}

		if (object.isAnnotationPresent(CustomAnnotationClass.class)) {
			// Gets the desired annotation
			Annotation annotation = object.getAnnotation(CustomAnnotationClass.class);
			System.out.println(annotation);
		}

		// the same for all methods of the class
		for (Method method : object.getDeclaredMethods()) {
			if (method.isAnnotationPresent(CustomAnnotationMethod.class)) {
				Annotation annotation = method.getAnnotation(CustomAnnotationMethod.class);
				System.out.println(annotation);
			}
		}
	}

}
