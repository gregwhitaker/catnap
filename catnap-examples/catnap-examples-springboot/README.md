# catnap-examples-springboot
This example shows you how to configure Catnap view rendering with the Spring Boot web framework.

## Running the Example
Start the example by running the following command:

```
$ ./run.sh
```

## API
This example application exposes the following API for retrieving information about Widgets.

### Retrieve all Widgets

* [http://localhost:8080/widgets](http://localhost:8080/widgets)

### Retrieve a Single Widget

* [http://localhost:8080/widgets/1](http://localhost:8080/widgets/1)

## Example Queries

Catnap allows consumers a great deal of flexibility to return only the data in which they are interested.  The example
API calls below illustrate the power of Catnap.  Feel free to play around with the examples and come up with your 
own Catnap queries.

### Retrieve Selected Fields
This example will only retrieve the name of the widget and all of its images.

* [http://localhost:8080/widgets/1?fields=name,images](http://localhost:8080/widgets/1?fields=name,images)

```
{
  "name": "Widget 1",
  "images": [
    {
      "sortOrder": 1,
      "url": "http://gregwhitaker.github.com/catnap/widgets/1/images/large.png",
      "alt": "Widget 1 Large",
      "type": "large"
    },
    {
      "sortOrder": 2,
      "url": "http://gregwhitaker.github.com/catnap/widgets/1/images/medium.png",
      "alt": "Widget 1 Medium",
      "type": "medium"
    },
    {
      "sortOrder": 3,
      "url": "http://gregwhitaker.github.com/catnap/widgets/1/images/thumbnail.png",
      "alt": "Widget 1 Thumbnail",
      "type": "thumbnail"
    }
  ]
}
```
### Retrieving Selected Fields in Nested Objects
This example will only retrieve the name of the widget, the formatted list price, and the formatted sale price.

* <a href="http://localhost:8080/widgets/1?fields=name,prices(formattedList,formattedSale)">http://localhost:8080/widgets/1?fields=name,prices(formattedList,formattedSale)</a>

```
{
  "name": "Widget 1",
  "prices": {
    "formattedList": "$35.99",
    "formattedSale": "$30.00"
  }
}
```

### Retrieving Selected Fields with Conditionals
This example will only retrieve the name of the widget and the url of the thumbnail image for the widget.

* <a href="http://localhost:8080/widgets/1?fields=name,images(url)[type=thumbnail]">http://localhost:8080/widgets/1?fields=name,images(url)[type=thumbnail]</a>

```
{
  "name": "Widget 1",
  "images": [
    {
      "url": "http://gregwhitaker.github.com/catnap/widgets/1/images/thumbnail.png"
    }
  ]
}
```

### Filtering Lists with Conditionals
This example will only retrieve the name of the widget and the urls of images that have a sortOrder greater than or equal to 2.

* <a href="http://localhost:8080/widgets?fields=name,images(sortOrder,url)[sortOrder&gt=2]">http://localhost:8080/widgets?fields=name,images(url)[sortOrder>=2]</a>

```
[
  {
    "name": "Widget 1",
    "images": [
      {
        "url": "http://gregwhitaker.github.com/catnap/widgets/1/images/medium.png"
      },
      {
        "url": "http://gregwhitaker.github.com/catnap/widgets/1/images/thumbnail.png"
      }
    ]
  },
  {
    "name": "Widget 2",
    "images": [
      {
        "url": "http://gregwhitaker.github.com/catnap/widgets/2/images/thumbnail.png"
      }
    ]
  },
  {
    "name": "Widget 3",
    "images": [
      {
        "url": "http://gregwhitaker.github.com/catnap/widgets/3/images/medium.png"
      },
      {
        "url": "http://gregwhitaker.github.com/catnap/widgets/3/images/thumbnail.png"
      }
    ]
  }
]
```

## JSON representation
You can customize the JSON rendering using the the Jackson ObjectMapper customizations provided by spring-boot.

See <a href="https://docs.spring.io/spring-boot/docs/current/reference/html/howto-spring-mvc.html#howto-customize-the-jackson-objectmapper">"Customize the Jackson ObjectMapper"</a> section in Spring Boot documentation.

* Display dates as timestamps

![write_dates_as_timestamps](docs/images/write_dates_as_timestamps_true.png)

* Display dates as strings (UTC)

![write_dates_as_UTC_strings](docs/images/write_dates_as_timestamps_false.png)

