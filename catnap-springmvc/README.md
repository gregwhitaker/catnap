Catnap SpringMVC Integration
===
Catnap can be configured in two different ways within Spring.  You can either use the ```@EnableCatnapWebMvc``` annotation that provides a sensible default configuration that is useful for most applications or you can configure the views and view resolver yourself.  

In either case you first need to include a reference to the catnap-springmvc library which provides integration between Spring and the Catnap framework.

```
<dependency>
   	<groupId>com.catnap</groupId>
   	<artifactId>catnap-springmvc</artifactId>
   	<version>1.8</version>
</dependency>
```

####Configuring Catnap using @EnableCatnapWebMvc
The simplest approach to enable Catnap view rendering in Spring is to annotate your ```@Configuration``` class with the ```@EnableCatnapWebMvc``` annotation.  By annotating your configuration class with ```@EnableCatnapWebMvc``` you are enabling Catnap to register itself automatically with your spring context on startup.

**NOTE:** *Catnap will register view support for JSON, JSONP, and XML responses by default.*

	@Configuration
	@ComponentScan(basePackages = "com.catnap.demo.springmvc.controller")
	@Import(ApplicationConfiguration.class)
	@EnableCatnapWebMvc
	public class WebMvcConfiguration {
    	//Your spring configuration goes here...
	}

If you would like to override the settings on any of the default configured views you can simply create an overriding bean definition as shown below:

	@Configuration
	@ComponentScan(basePackages = "com.catnap.demo.springmvc.controller")
	@Import(ApplicationConfiguration.class)
	@EnableCatnapWebMvc
	public class WebMvcConfiguration {
    	@Bean
    	public CatnapWrappingView jsonCatnapSpringView() {
    	
        	//Configuring CatnapView with custom ObjectMapper
        	ObjectMapper mapper = new ObjectMapper();
        	mapper.enable(SerializationFeature.INDENT_OUTPUT);
         
        	return new CatnapWrappingView(new JsonCatnapView.Builder()
                	.withObjectMapper(mapper)
                	.build());
    	}
	}
	
####Configuring Catnap by extending WebMvcConfigurationSupport
Follow the steps below (*configuration steps assume you are using the Annotation configuration approach*):

1. If you are using the ```@EnableWebMvc``` annotation on your configuration, remove it and instead extend ```WebMvcConfigurationSupport```.  This will allow greater control over Spring's configuration.

2. In your WebMVC configuration class override the ```configureContentNegotiation``` method and register the content-types supported by your application.

		@Override
		protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    		configurer.defaultContentType(MediaType.APPLICATION_JSON);
    		configurer.favorPathExtension(true);
    		configurer.ignoreAcceptHeader(false);
    		configurer.mediaType("json", MediaType.APPLICATION_JSON);
    		configurer.mediaType("jsonp", new MediaType("application", "x-javascript"));
    		configurer.mediaType("xml", MediaType.APPLICATION_XML);
    		super.configureContentNegotiation(configurer);
		}
		
3. In your WebMVC configuration class, register a CatnapWrappingView bean for each of the Catnap view types you wish to support in your application.

		@Bean
		public CatnapWrappingView jsonCatnapSpringView() {
    		return new CatnapWrappingView(new JsonCatnapView.Builder().build());
		}
 
		@Bean
		public CatnapWrappingView jsonpCatnapSpringView() {
    		return new CatnapWrappingView(new JsonpCatnapView.Builder().build());
		}
 
		@Bean
		public CatnapWrappingView xmlCatnapSpringView() {
    		return new CatnapWrappingView(new XmlCatnapView.Builder()
            	.withMarshallerSchemaLocation("http://www.test.com/schema http://www.test.com/schema/test_schema.xsd")
            	.build());
		}
		
4. In your WebMVC configuration class, register a ```ContentNegotiationViewResolver```.

		@Bean
		public ContentNegotiatingViewResolver contentNegotiatingViewResolver() {
    		List<View> defaultViews = new ArrayList<View>(2);
    		defaultViews.add(jsonCatnapSpringView());
    		defaultViews.add(jsonpCatnapSpringView());
    		defaultViews.add(xmlCatnapSpringView());
    
    		List<CatnapWrappingView> catnapViews = new ArrayList<CatnapWrappingView>(2);
    		catnapViews.add(jsonCatnapSpringView());
    		catnapViews.add(jsonpCatnapSpringView());
    		catnapViews.add(xmlCatnapSpringView());
    
    		CatnapViewResolver catnapViewResolver = new CatnapViewResolver();
    		catnapViewResolver.setViews(catnapViews);
    
    		List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>(1);
    		viewResolvers.add(catnapViewResolver);
    
    		ContentNegotiatingViewResolver cnvr = new ContentNegotiatingViewResolver();
    		cnvr.setContentNegotiationManager(mvcContentNegotiationManager());
    		cnvr.setOrder(1);
    		cnvr.setDefaultViews(defaultViews);
    		cnvr.setViewResolvers(viewResolvers);
    
    		return cnvr;
		}
