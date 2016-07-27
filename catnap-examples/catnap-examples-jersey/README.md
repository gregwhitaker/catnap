catnap-examples-jersey
===

This example shows you how to configure Catnap view rendering with the Jersey web framework.

##API
This example application exposes the following API for retrieving information about Widgets.

###Retrieve all Widgets

* [http://localhost:8080/widgets](http://localhost:8080/widgets)

###Retrieve a Single Widget

* [http://localhost:8080/widgets/1](http://localhost:8080/widgets/1)

##Example Queries

Catnap allows consumers a great deal of flexibility to return only the data in which they are interested.  The example
API calls below illustrate the power of Catnap.  Feel free to play around with the examples and come up with your 
own Catnap queries.

###Retrieve Selected Fields
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
###Retrieving Selected Fields in Nested Objects
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

###Retrieving Selected Fields with Conditionals
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

###Filtering Lists with Conditionals
