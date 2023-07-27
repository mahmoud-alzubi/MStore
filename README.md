# MStore 🛒
<p align="justify">
MStore is a backend project for an online shopping store. It follows a parent-child Maven structure and adheres to Java modularity principles. Additionally, it includes microservices for Inventory, Orders, and Products, and utilizes MongoDB and MySQL databases. The project incorporates integration testing using Test Containers to ensure reliable and comprehensive system functionality testing.</p>

<p align="justify">To implement efficient RESTful APIs, MStore leverages the Java and Spring Boot framework. This framework allows for seamless management of inventory, order processing, and product information in the online store. </p>

<p align="justify">The microservices architecture of MStore is empowered by several technologies and design patterns. For service discovery and dynamic communication between microservices, MStore uses Netflix Eureka, a powerful service registry. This enables automated service registration and discovery, allowing microservices to locate and communicate with each other without explicit configuration. This approach improves scalability and fault tolerance, making it easy to add or remove microservices without disrupting the overall functionality. </p>

<p align="justify">To ensure secure access to the APIs, MStore integrates Keycloak, an OAuth2 identity and access management solution. This provides robust authentication and authorization mechanisms for protecting sensitive resources and ensuring data privacy.</p>

<p align="justify">For client-side load balancing, MStore utilizes a circuit breaker design pattern in combination with Resilience4J, a fault tolerance library. This helps prevent cascading failures and provides a graceful degradation of services during high load or failures in dependent services.</p>

<p align="justify">Additionally, MStore implements an API Gateway, which serves as the entry point for clients and acts as a reverse proxy to efficiently route and manage incoming requests. The API Gateway handles various responsibilities, including request routing, load balancing, caching, rate limiting, and security enforcement. This centralizes cross-cutting concerns and simplifies the client-side by providing a unified interface to interact with the microservices.</p>

<p align="justify">In addition to these features, MStore employs Apache Kafka for notifications. This allows real-time communication and event-driven architecture, enabling seamless updates and notifications to users, such as order status changes, product updates, or inventory alerts.</p>

<p align="justify">With these technologies and design patterns, MStore's backend offers a scalable, resilient, and efficient system for managing inventory, processing orders, and providing product information in the online store. </p>
