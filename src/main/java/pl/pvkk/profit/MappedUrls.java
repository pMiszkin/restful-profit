package pl.pvkk.profit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.thoughtworks.paranamer.AnnotationParanamer;
import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;

@RestController
public class MappedUrls {

	@Autowired
	private ApplicationContext context;
	
	
	/**
	 * it gets all REST and views mapped urls and shows accepted parameters 
	 * @return List<String>
	 */
	//TODO REFACTOR THAT
	@GetMapping("mappedurls")
	public List<String> getAllMappedUrls() {
		Map<String, RequestMappingHandlerMapping> matchingBeans = BeanFactoryUtils
				.beansOfTypeIncludingAncestors(context, RequestMappingHandlerMapping.class, true, false);
		List<String> list = new LinkedList<String>();
		if (!matchingBeans.isEmpty()) {
			ArrayList<HandlerMapping> handlerMappings = new ArrayList<HandlerMapping>(matchingBeans.values());
			AnnotationAwareOrderComparator.sort(handlerMappings);

			RequestMappingHandlerMapping mappings = matchingBeans.get("requestMappingHandlerMapping");
			
			Map<RequestMappingInfo, HandlerMethod> handlerMethods = mappings.getHandlerMethods();
			Paranamer info = new CachingParanamer(new AnnotationParanamer(new BytecodeReadingParanamer()));
			String params;
			
			//important part
			for(RequestMappingInfo requestMappingInfo : handlerMethods.keySet()) {
				RequestMethodsRequestCondition methods = requestMappingInfo.getMethodsCondition();
				Method method = handlerMethods.get(requestMappingInfo).getMethod();	
				
				String[] parameterNames = info.lookupParameterNames(method, false);
				params=" PARAMS: ";
				Parameter[] p = method.getParameters();
				for(int i=0; i<parameterNames.length; i++){
					for(Annotation a : p[i].getAnnotations())
						params+="@"+a.annotationType().getSimpleName()+" ";
					params +=p[i].getType().getSimpleName()+" "+parameterNames[i]+", ";
				}
				
				list.add(requestMappingInfo.getPatternsCondition().getPatterns()+
						" -> "+methods.getMethods()+params);
			}
		}
		return list;
	}
}
