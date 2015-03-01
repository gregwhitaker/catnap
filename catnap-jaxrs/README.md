Catnap JAX-RS Integration
===
Catnap views are implemented as JAX-RS Providers and can be configured as such:

*	Auto-detected at application startup via classpath scanning
*	Added to the application context by extending the JAX-RS ```Application``` bootstrap class
*	Servlet context parameters

In all cases you first need to include a reference to the catnap-jaxrs library which provides integration between any JAX-RS compliant framework and Catnap.

```
<dependency>
   	<groupId>com.catnap</groupId>
   	<artifactId>catnap-jaxrs</artifactId>
   	<version>1.8</version>
</dependency>
```

####Configuring Catnap using Auto-Detection
This is the simplest of the configuration steps and is the recommended approach if the default Catnap view configuration works for your application.  All you have to do is include a reference to the catnap-jaxrs library in your application and JAX-RS ```@Provider``` scanning will take care of locating and registering Catnap views for JSON, JSONP, and XML responses.

If you want to support href suffix content negotiation then you will need to configure your web framework to resolve the suffixes to appropriate content types.  The method for doing this differs between Resteasy and Jersey.  

#####Resteasy
In order to enable href suffix content negotiation in Resteasy you need to add the following to your **web.xml** file.

```
<context-param>
    <param-name>resteasy.media.type.mappings</param-name>
    <param-value>json : application/json, jsonp : application/x-javascript, xml : application/xml</param-value>
</context-param>
```

#####Jersey
Jersey requires you to implement a custom content negotiation filter and register it with your **web.xml** file.

	public class MediaTypeFilter extends UriConnegFilter {
    	private static final Map<String, MediaType> mediaExtensions = new HashMap<String, MediaType>(3);
    	
    	static {
        	mediaExtensions.put("json", MediaType.APPLICATION_JSON_TYPE);
        	mediaExtensions.put("jsonp", new MediaType("application", "x-javascript"));
        	mediaExtensions.put("xml", MediaType.APPLICATION_XML_TYPE);
    	}
    	
    	public MediaTypeFilter() {
        	super(mediaExtensions);
    	}
	}
	
```
<servlet>
    <servlet-name>jersey-serlvet</servlet-name>
    <servlet-class>com.sun.jersey.spi.container.servlet.ServletContainer</servlet-class>
    <init-param>
        <param-name>javax.ws.rs.Application</param-name>
        <param-value>com.catnap.demo.jersey.JerseyDemoApplication</param-value>
    </init-param>
    <init-param>
        <param-name>com.sun.jersey.spi.container.ContainerRequestFilters</param-name>
        <param-value>com.catnap.demo.jersey.filter.MediaTypeFilter</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
</servlet>
```

####Configuring Catnap using Application Bootstrap Class
Catnap supports configuration by adding the Catnap MessageBodyWriters to the Providers singletons list in the ```javax.ws.rs.Application``` bootstrap class as shown below.  

This is the preferred configuration mechanism if you would like to customize the settings of the individual views.  For example, you would use this configuration mechanism if you wanted to change the properties of the ObjectWriter used when rendering JSON responses.

	public class JaxrsDemoApplication extends Application {
    	private Set<Object> singletons = new HashSet<Object>();
 
    	public JaxrsDemoApplication () {
        	//Add Resources
        	singletons.add(new ProductResource());
        	singletons.add(new ProductFamilyResource());
 
        	//Add Catnap MessageBodyWriters (Note: These can also be auto-detected via their @Provider annotations)
        	singletons.add(new JsonCatnapMessageBodyWriter());
        	singletons.add(new JsonpCatnapMessageBodyWriter());
        	singletons.add(new XmlCatnapMessageBodyWriter());
    	}
 
    	@Override
    	public Set<Object> getSingletons() {
        	return singletons;
    	}
	}
