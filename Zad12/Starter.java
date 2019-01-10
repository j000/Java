import java.util.function.Consumer;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

@SuppressWarnings({"unchecked", "rawtypes"})
class Starter implements Consumer<String> {
	public void execute(Object instance, Method method, String... strings)
		throws IllegalAccessException, InvocationTargetException
	{
		for (int i = method.getAnnotation(MethodToStart.class).value(); i > 0;
			 --i) {
			method.invoke(instance, (Object[])strings);
		}
	}

	@Override
	public void accept(String name)
	{
		try {
			final Class c = Class.forName(name);
			List<Method> allMethods
				= new ArrayList<>(Arrays.asList(c.getMethods()));

			// MethodToStart
			allMethods.removeIf(
				m -> !m.isAnnotationPresent(MethodToStart.class));
			// MethodDisabled
			allMethods.removeIf(
				m -> m.isAnnotationPresent(MethodDisabled.class));
			// parameters
			allMethods.removeIf(m -> {
				Class params[] = m.getParameterTypes();
				if (params.length > 1)
					return true;
				if (params.length == 0)
					return false;
				return !(params[0].equals(String.class));
			});

			// tworzymy instancję
			final Object instance = c.newInstance();

			// odpalamy
			for (Method method : allMethods) {
				Class params[] = method.getParameterTypes();
				if (params.length == 0) {
					execute(instance, method);
				} else {
					String arg = null;
					try {
						arg = method.getAnnotation(StringParameter.class)
								  .value();
					} catch (NullPointerException e) {
					}
					execute(instance, method, arg);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
