package hw.cvetkov;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@SupportedAnnotationTypes("hw.cvetkov.CustomToString")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class CustomToStringProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            return false;
        }

        for (Element element : roundEnv.getElementsAnnotatedWith(CustomToString.class)) {
            TypeElement typeElement = (TypeElement) element;
            String className = typeElement.getQualifiedName().toString();
            String generatedClassName = className + "ToString";

            try {
                writeClass(typeElement, generatedClassName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return true;
    }

    private void writeClass(TypeElement typeElement, String generatedClassName) throws IOException {
        String packageName = processingEnv.getElementUtils().getPackageOf(typeElement).toString();

        try (PrintWriter out = new PrintWriter(processingEnv.getFiler().createSourceFile(generatedClassName).openWriter())) {
            out.println("package " + packageName + ";");
            out.println();
            out.println("public class " + generatedClassName + " {");
            out.println("    private " + typeElement.getSimpleName() + " object = new " + typeElement.getSimpleName() + "();");
            out.println();
            out.println("    public String customToString() {");
            out.println("        return \"" + generatedClassName + " [\" +");

            for (Element fieldElement : typeElement.getEnclosedElements()) {
                if (fieldElement.getKind().isField() && !fieldElement.getModifiers().contains(Modifier.STATIC)) {
                    VariableElement variableElement = (VariableElement) fieldElement;
                    String fieldName = variableElement.getSimpleName().toString();
                    String strUpperFirst = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

                    out.println("                \"" + strUpperFirst + "=\" + object.get" + strUpperFirst + "() + \", \" +");
                }
            }

            out.println("                \"]\";");
            out.println("    }");
            out.println("}");
        }
    }
}