gulp
====

gulp (because it's not SWIG), is a library to create wrappers for Java code to be used in R (via rJava).
If you are in an early development stage, and constantly refactor your code it will avoid the need to manually update bindings in the rJava counterpart and be able to process them programatically (using your own `TemplateProcessor`s to generate the R wrapper.

Installation
============

To install, simply run maven:

    mvn clean install
    
and add the dependency to your project:

    <dependency>
        <groupId>gulp</groupId>
        <artifactId>gulp</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>

Usage
=====

gulp provides annotations which can be used to flag references which you want to process and ultimately create a R script wrapping them for usage in rJava.

All classes which contain methods to be exported (or themselves to be exported) must be marked with the annotation `@R`.

Additionally, if the class itself needs to be referenced, `@ExportClassReference` must be used.
If a method is to be exported, `@ExportMethodReference` is used.

For instance, let us suppose we have a class `MyClass` we wish to export. We write:

    @R
    @ExportClassReference(reference = "myClass")
    public class MyClass {
    
    }
    
If you want to export method bindings you can simply write:

    @R
    public class AnotherClass {
        @ExportMethodReference(reference = "testOne")
        public static Double testOne() {
            return 2d;
        }
        @ExportMethodReference(reference = "testTwo")
        public static String testTwo() {
            return "works!";
        }
    }
    
As you can see, the `reference` value in the annotations refer to what the binding will be actually called in R.
This way you can refactor code relentlessly witouth worrying about breaking the R calling code.

Once you have declared the reference entries you can process them simply by specifying the root pacakge to be scanned (the scan is performed recursively).

        AnnotationProcessor processor = new AnnotationProcessor("org.ruivieira.gulp");
        processor.process();
        
This will generate a `Set<Reference>` of references which can be passed into a `TemplateProcessor`.
Your template processor can transform and persist the references as you want.
As an example, you could pass them to FreeMarker and use your own template to generate the wrapper script.

To get you started, there is a simple processor included which uses `StringBuilder` to create a simple script:

        TemplateProcessor myProcessor = new StringBuilterTemplateProcessor();
        String result = myProcessor.process(processor.getReferenceMap());
        System.out.println(result);

This will produce (using the previous annotated classes):

      library(rJava)
      
      init <- function(classpath=c()) {
      	if (!is.null(classpath)) {
      
      		# initialise the JVM
      			.jinit()
      
      		for (path in classpath) {
      			extension <- file_ext(path)
      			if (extension=="") {
      				.jaddClassPath(dir(path, full.names=TRUE ))
      			} else {
      				.jaddClassPath(path)
      			}
      		}
      	}
      
      	testTwo <- J("org.ruivieira.gulp.test.AnotherClass.testTwo")
      	myClass <- J("org.ruivieira.gulp.test.MyClass")
      	testOne <- J("org.ruivieira.gulp.test.AnotherClass.testOne")
      }

Notes
=====

This is alpha stage, if you have any comments or suggestions, please leave them here!
