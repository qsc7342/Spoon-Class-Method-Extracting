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
    public static Launcher getLaunch(String path) {
        System.out.println("=================================");
        System.out.println("Launching launcher...");
        Launcher launcher = new Launcher();
        CodeFactory codeFactory = new CodeFactory((launcher.createFactory()));
        launcher.addInputResource(path);
        launcher.buildModel();
        System.out.println("Done");
        System.out.println("=================================");
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

    public static Map<String, List<CtMethod> > getMethod(CtClass classes) {
        Map<String, List<CtMethod> > methods = new HashMap<>();
        String className = classes.getSimpleName();
        methods.put(className, new ArrayList<>(classes.getMethods()));
        return methods;
    }

    public static Map<String, List<CtParameter> > getParams(CtMethod method) {
        Map<String, List<CtParameter> > params = new HashMap<>();
        params.put(method.getSimpleName(), new ArrayList<>(method.getParameters()));
        return params;
    }
    public static void main(String[] args) {
        Launcher launcher = getLaunch("C:/Users/ninte/Desktop/workspace");
        Launcher launcher2 = getLaunch("C:/Users/ninte/Desktop/workspace2");
//        try {
//            Launcher launcher2 = getLaunch("C:/Users/ninte/Desktop/workspace2");
//        }
//        catch(SpoonException e) {
//            /*
//                Class 이름이 같을 경우 처리 필요
//             */
//            System.out.println("check");
//            e.printStackTrace();
//        }

//        System.out.println("Build Model...");
        CtModel model = getModel(launcher);
        CtModel model2 = getModel(launcher2);
        System.out.println("Extract All Classes");
        List<CtClass> classList = model.getElements(new TypeFilter<>(CtClass.class));
        List<CtClass> classList2 = model2.getElements(new TypeFilter<>(CtClass.class));
        System.out.print(classList.size());
        System.out.println(" Classes in Project");
        System.out.print(classList2.size());
        System.out.println(" Classes in Project2");
        for(CtClass ctclass: classList) {
            String ref = ctclass.getReference().toString();
            String[] path = ref.split("\\.");
            System.out.println("Package : " + path[0] + "  Class : " + path[1]);
        }

        System.out.println("Extract All Methods...");
        Map<String, List<CtMethod>> allMethods = new HashMap<>();
        for (CtClass ctClass : classList) {
            String cName = ctClass.getSimpleName();
            System.out.println("CName : " + cName);
            cName = cName.substring(0, cName.lastIndexOf("_") < 0 ? cName.length() : cName.lastIndexOf("_"));
            if (allMethods.containsKey(cName)) {
                allMethods.get(cName).addAll(new ArrayList<>(ctClass.getMethods()));
            } else {
                allMethods.put(cName, new ArrayList<>(ctClass.getMethods()));
            }
        }

        /*
            메소드 이름의 추출
            mname에는 각 메소드의 바디까지 출력된다.
            getSimpleName()을 이용해 메소드의 이름만을 추출 가능.
         */
        for(Map.Entry<String, List<CtMethod> > method: allMethods.entrySet()) {
            System.out.println("============================================");
            System.out.println("Class Name : " + method.getKey());
            for(CtMethod mname: method.getValue()) {
//                System.out.println("Reference : " + mname.getPath());
                System.out.println("method : " + mname.getSimpleName());
//                System.out.println("body : " + mname);
                Map<String, List<CtParameter> > Params = new HashMap<>();
                Params.put(mname.getSimpleName(), new ArrayList<>(mname.getParameters()));
                for(Map.Entry<String, List<CtParameter> > param: Params.entrySet()) {
                  System.out.println("Parameters : " + param.getValue());
                }
            }
            System.out.println("============================================");
        }
        System.out.println("Done");

    }
}