import spoon.Launcher;
import spoon.reflect.*;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.factory.CodeFactory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.Filter;
import spoon.reflect.visitor.filter.InvocationFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.*;

import java.util.List;

class SpoonTest {
    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        CodeFactory codeFactory = new CodeFactory(launcher.createFactory());
        launcher.addInputResource("C:/Users/ninte/Desktop/workspace/Test");

        System.out.println("Build Model...");
        launcher.buildModel();
        CtModel model = launcher.getModel();
        System.out.println("Done");

        System.out.println("Extract All Classes");
        List<CtClass> classList = model.getElements(new TypeFilter<>(CtClass.class));
        System.out.print(classList.size());
        System.out.println(" Classes in Project");

        for(CtClass ctclass: classList) {
            System.out.println(ctclass.getSimpleName());
        }
    }
}