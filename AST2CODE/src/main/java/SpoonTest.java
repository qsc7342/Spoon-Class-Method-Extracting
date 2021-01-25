import spoon.Launcher;
import spoon.SpoonException;
import spoon.compiler.ModelBuildingException;
import spoon.reflect.*;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtModule;
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

import java.io.IOException;
import java.util.List;
import java.util.*;

class SpoonTest {
    private static CtMethod method;

    public static Launcher getLaunch(String path) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("Launching launcher...");
        Launcher launcher = new Launcher();
        CodeFactory codeFactory = new CodeFactory((launcher.createFactory()));
        launcher.addInputResource(path);
        launcher.buildModel();
        System.out.println("Done");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return launcher;
    }

    public static List<CtClass> getClass(CtModel model) {
        List<CtClass> classList = model.getElements(new TypeFilter<>(CtClass.class));
        return classList;
    }

    public static String[] getRef(CtClass classes) {
        String ref = classes.getReference().toString();
        String[] path = ref.split("\\.");
        return path;
    }

    public static CtModel getModel(Launcher launcher) {
        CtModel model = launcher.getModel();
        return model;
    }

    public static Map<String, List<CtParameter> > getParams(Map<String, List<CtParameter> > par, CtMethod methods) {
        String methodName = methods.getSimpleName();
        if(par.containsKey(methodName)) {
            par.get(methodName).addAll(new ArrayList<>(methods.getParameters()));
        }
        else {
            par.put(methodName, new ArrayList<>(methods.getParameters()));
        }
        return par;
    }

    public static Map<String, List<CtMethod> > getMethod(CtClass classes) {
        Map<String, List<CtMethod> > methods = new HashMap<>();
        String className = classes.getSimpleName();
        if (methods.containsKey(className)) {
            methods.get(className).addAll(new ArrayList<>(classes.getMethods()));
        } else {
            methods.put(className, new ArrayList<>(classes.getMethods()));
        }
        return methods;
    }


    public static void main(String[] args) {
        Launcher launcher = getLaunch("C:/Users/ninte/Desktop/workspace");
        CtModel model = getModel(launcher);
        List<CtClass> classList = getClass(model);
        for(CtClass classes: classList) {
            String[] ref = getRef(classes);
            System.out.println("===========================================");
            System.out.println("Package Name : " + ref[0]);
            System.out.println("Class Name : " + ref[1]);
            Map<String, List<CtMethod> > methods = getMethod(classes);
            Map<String, List<CtParameter> > params = new HashMap<>();
            for(Map.Entry<String, List<CtMethod> > method: methods.entrySet()) {
                for(CtMethod current: method.getValue()) {
                    params = getParams(params, current);
                }
                for(Map.Entry<String, List<CtParameter> > parameter: params.entrySet()) {
                    System.out.println("Method Name : " + parameter.getKey());
                    System.out.println("Parameter : " + parameter.getValue());
                }
            }
            System.out.println("===========================================");
        }
    }
}