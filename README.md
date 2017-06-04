# catnap
[![Build Status](https://travis-ci.org/gregwhitaker/catnap.svg?branch=catnap2)](https://travis-ci.org/gregwhitaker/catnap)

Catnap is a framework for supporting partial JSON and JSONP responses in RESTful web services by allowing users to supply arbitrary queries in the URL.

Catnap supports partial responses in the following web frameworks:

* 	Spring Boot
* 	SpringMVC
* 	RESTEasy
* 	Jersey

## What is a partial response?
By default, the server will send back the full representation of a rest resource for every request.  Partial responses let you request only the elements you are interested in, instead of the full resource representation.  This allows your client application to avoid transferring, parsing, and storing unneeded fields, so you can utilize network and memory resources more efficiently.

For example, take the two responses below.  Both are requests for the same resource, but let's assume we are only interested in the following fields:

*	Product Name
*	List Price
*	Image Url for Thumbnail Images

### Full Resource Representation
[https://www.catnap.it/products/12345/details](https://catnap-springboot-sample.herokuapp.com/products/12345/details)

	{
	  "id": "12345",
	  "name": "Product 1",
	  "prices": {
	    "list": "$120.00",
	    "sale": "89.99"
	  },
	  "images": [
	    {
	      "sortOrder": 1,
	      "url": "https://catnap-springboot-sample.herokuapp.com\/12345-primary.png",
	      "alt": "Product 1",
	      "size": "primary"
	    },
	    {
	      "sortOrder": 2,
	      "url": "https://catnap-springboot-sample.herokuapp.com\/12345-thumbnail.png",
	      "alt": "Product 1",
	      "size": "thumbnail"
	    }
	  ]
	}
	
### Partial Resource Representation
[https://www.catnap.it/products/12345/details?fields=name,prices(list),images(url)[size=thumbnail]](https://catnap-springboot-sample.herokuapp.com/products/12345/details?fields=name,prices(list),images(url)[size=thumbnail])

	{
	  "name" : "Product 1",
	  "prices" : {
	    "list" : "$120.00"
	  },
	  "images" : [ {
	    "url" : "https://catnap-springboot-sample.herokuapp.com/12345-thumbnail.png"
	  } ]
	}
	
As you can see the partial response is a significant reduction in payload size and message complexity.  By allowing the consumer of the API to specify the fields they are interested in you can significantly reduce the complexity of response messages as well as improve performance over the wire.

## Getting Catnap
Catnap libraries are available from JCenter.

* [catnap-spring](https://bintray.com/gregwhitaker/maven/catnap-spring) - Use this library if you are integrating Catnap with a SpringMVC application.
* [catnap-springboot](https://bintray.com/gregwhitaker/maven/catnap-springboot) - Use this library if you are integrating Catnap with a Spring Boot application.
* [catnap-jaxrs](https://bintray.com/gregwhitaker/maven/catnap-jaxrs) - Use this library if you are integrating Catnap with a Jersey or RESTEasy application.

## Getting Started with Catnap

Please see the [wiki](https://github.com/gregwhitaker/catnap/wiki) for detailed documentation on how to get started using Catnap.

For documentation on the Catnap Query Language, please consult the [Catnap Query Language Reference](https://github.com/gregwhitaker/catnap/wiki/Catnap-Query-Language-Reference).

## Examples

Please see the included [example projects](catnap-examples) for demonstrations on how to configure and use Catnap with your favorite web framework.

## Bugs and Feedback

For bugs, questions and discussions please use the [Github Issues](https://github.com/gregwhitaker/catnap/issues).

## License
Copyright 2016 Greg Whitaker

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
