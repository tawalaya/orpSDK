Open Recommendation Platform SDK
================
> The Open Recommendation Platform (ORP) is a distributed platform of entities capable of delivering recommendations for various purposes. It consists of recommendation providers and recommendation consumers that interact and communicate over a standardized protocol. This document describes the protocol and outlines the necessary steps a partner needs to take in order to integrate a technology as recommendation provider. 

See more @ [Open Recommendation Platform Description](http://orp.plista.com/documentation/download)

This projects aims to provide a Java implementation to take part in the Open Recommendation Platform. It has been developed within the context of a bachelor thesis.
All Components of this SDK are written in Java and need the 1.8 SDK since some parts of this project are using newer features of Java. All dependencies are mannaged with maven 2.

Overview
----
This Project consists of two parts, a SDK to interact with the ORP System (orpSDK) and a SDK to test other implementatios that are desinged for the ORP System (orpTestSDK). 


#orpSDK Overview
The orpSDK consists of a jetty compontent that handels comunication with the orp servers. All recommendations, item updates and impressions are forworded to a MultiDomain Recommender witch needs to be implemented to use this framework. 

![alt text](... "orpSDK class diagram")

The Frameworks core component is the Recommendation Interface. It contains a MultiDomain and SingleDomain interface. Each has functions to prosses impressions, updates,
and recomendation request. Asided from both interfaces have methodes to agressivly free memmory, persist the state of the algorithmen as well as restore form persisted files. 

#orpTestSDK Overview
The TestSDK is a set of classes and interfaces to send, receive and evalueate messages from a orp service implementation.

![alt text](... "orpTestSDK class diagram")

#Licence
LGPL v.3 