package com.csosa.fntest.sample

val my_taxi = """
{
  "poiList": [
    {
      "id": 770827,
      "coordinate": {
        "latitude": 53.63774681071923,
        "longitude": 10.077876811952127
      },
      "fleetType": "TAXI",
      "heading": 353.90969927548304
    },
    {
      "id": 485507,
      "coordinate": {
        "latitude": 53.504172654173416,
        "longitude": 9.952611993677236
      },
      "fleetType": "TAXI",
      "heading": 77.48096934318164
    },
    {
      "id": 483644,
      "coordinate": {
        "latitude": 53.45314848249459,
        "longitude": 10.090219476376275
      },
      "fleetType": "TAXI",
      "heading": 248.89686386855803
    },
    {
      "id": 608837,
      "coordinate": {
        "latitude": 53.62331120006383,
        "longitude": 9.817436067951062
      },
      "fleetType": "TAXI",
      "heading": 35.69576681552935
    },
    {
      "id": 700356,
      "coordinate": {
        "latitude": 53.47798888033693,
        "longitude": 9.777031798728142
      },
      "fleetType": "POOLING",
      "heading": 316.33620626857436
    },
    {
      "id": 144834,
      "coordinate": {
        "latitude": 53.59765803017325,
        "longitude": 10.064919371147772
      },
      "fleetType": "TAXI",
      "heading": 356.3258478580606
    },
    {
      "id": 239699,
      "coordinate": {
        "latitude": 53.65264719096622,
        "longitude": 9.765268743925851
      },
      "fleetType": "TAXI",
      "heading": 269.9225901849583
    },
    {
      "id": 194669,
      "coordinate": {
        "latitude": 53.540763194151566,
        "longitude": 9.925392270942064
      },
      "fleetType": "POOLING",
      "heading": 120.27991660926986
    }
  ]
}

""".trimIndent()

val google_matrix = """
{
  "destination_addresses" : [
    "Ahornstraße 38, 25462 Rellingen, Alemania",
    "Brackhövel, 21109 Hamburg, Alemania",
    "Jahnkeweg 15, 22179 Hamburg, Alemania",
    "K42 57, 21629 Neu Wulmstorf, Alemania",
    "510028, 21129 Hamburg, Alemania",
    "Altländer Str. 12, 20095 Hamburg, Alemania",
    "Von-Sauer-Straße 31E, 22761 Hamburg, Alemania",
    "Elbpromenade, 20459 Hamburg, Alemania"
  ],
  "origin_addresses" : [ "Neumühler Kirchenweg, 22767 Hamburg, Alemania" ],
  "rows" : [
    {
      "elements" : [
        {
          "distance" : {
            "text" : "16.8 km",
            "value" : 16786
          },
          "duration" : {
            "text" : "23 min",
            "value" : 1352
          },
          "status" : "OK"
        },
        {
          "distance" : {
            "text" : "14.7 km",
            "value" : 14677
          },
          "duration" : {
            "text" : "23 min",
            "value" : 1350
          },
          "status" : "OK"
        },
        {
          "distance" : {
            "text" : "15.2 km",
            "value" : 15247
          },
          "duration" : {
            "text" : "33 min",
            "value" : 1965
          },
          "status" : "OK"
        },
        {
          "distance" : {
            "text" : "36.7 km",
            "value" : 36709
          },
          "duration" : {
            "text" : "36 min",
            "value" : 2167
          },
          "status" : "OK"
        },
        {
          "distance" : {
            "text" : "19.2 km",
            "value" : 19218
          },
          "duration" : {
            "text" : "23 min",
            "value" : 1406
          },
          "status" : "OK"
        },
        {
          "distance" : {
            "text" : "6.0 km",
            "value" : 6013
          },
          "duration" : {
            "text" : "13 min",
            "value" : 799
          },
          "status" : "OK"
        },
        {
          "distance" : {
            "text" : "4.7 km",
            "value" : 4702
          },
          "duration" : {
            "text" : "11 min",
            "value" : 663
          },
          "status" : "OK"
        },
        {
          "distance" : {
            "text" : "3.6 km",
            "value" : 3649
          },
          "duration" : {
            "text" : "7 min",
            "value" : 418
          },
          "status" : "OK"
        }
      ]
    }
  ],
  "status" : "OK"
}

""".trimIndent()
