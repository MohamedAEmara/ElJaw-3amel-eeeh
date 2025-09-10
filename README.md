# Weather Recommendations API

A comprehensive weather recommendations system for the Egyptian cities built with Spring Boot that provides real-time weather data and AI-powered recommendations using Google Gemini AI and OpenWeatherMap API.

## Features

- **Weather Data**: Real-time weather information retrieval using OpenWeatherMap API
- **AI-Powered Recommendations**: Get intelligent weather-based recommendations using Google Gemini AI
- **City Management**: Support for multiple cities with Arabic and English names
- **Streaming Responses**: Real-time streaming of AI recommendations
- **OAuth with Gmail**: Authenticate users with gmail
- **Daily Quota**: allow only 5 requests / day for every user to "/weather" eandpoints
- **Database Integration**: PostgreSQL database with Flyway migrations
- **RESTful API**: Clean and intuitive REST endpoints

## Technologies Used
![technologies](https://go-skill-icons.vercel.app/api/icons?i=java,spring,postgres,gemini,gmail,git,github,docker&theme=dark)

## Tech Stack

- **Backend**: Spring Boot 3.5.5
- **Database**: PostgreSQL
- **AI Integration**: Google Gemini AI
- **Weather API**: OpenWeatherMap
- **Database Migration**: Flyway
- **OAuth2 Client**: authentication
- **Build Tool**: Maven
- **Java Version**: 21
- **Containerization**: Docker Compose

## Prerequisites

Before running this application, make sure you have the following installed:

- Java 21 or higher
- Maven 3.6 or higher
- Docker and Docker Compose
- PostgreSQL (if running locally without Docker)

## API Keys Setup

You'll need to obtain API keys from:

1. **OpenWeatherMap API**: Get your API key from [OpenWeatherMap](https://openweathermap.org/api)
2. **Google Gemini API**: Get your API key from [Google AI Studio](https://aistudio.google.com/)
3. **Google Cloud Platform Console**: Get you API key [Oauth Consent Screen](https://developers.google.com/workspace/guides/configure-oauth-consent)

Update the `src/main/resources/application.properties` file with your API keys:

```properties
openweathermap.api.key=YOUR_OPENWEATHERMAP_API_KEY
gemini.api.key=YOUR_GEMINI_API_KEY
spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_API_KEY
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_API_SECRET
```

## Installation & Setup

### Using Docker Compose (Recommended)

1. **Clone the repository**
   ```bash
   git clone git@github.com:MohamedAEmara/ElJaw-3amel-eeeh.git
   cd ElJaw-3amel-eeeh
   ```

2. **Start the PostgreSQL database**
   ```bash
   docker-compose up -d
   ```

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

## API Endpoints

### Weather Endpoints

- **GET** `/weather/{cityKey}` - Get current weather for a city
  - `cityKey`: Unique identifier for the city
  - Returns: Current weather data

- **GET** `/weather/recommendations/{cityKey}` - Get AI-powered weather recommendations
  - `cityKey`: Unique identifier for the city  
  - Returns: Streaming AI recommendations based on current weather

### City Endpoints

- **GET** `/cities` - Get all cities
- **GET** `/cities/{id}` - Get city by ID

### AI Integration Endpoints

- **POST** `/gemini/chat` - Direct chat with Gemini AI
- **GET** `/gemini/stream` - Streaming chat with Gemini AI

## Database Schema

### Cities Table
```sql
CREATE TABLE cities (
    id BIGSERIAL PRIMARY KEY,
    en_name VARCHAR(255),
    ar_name VARCHAR(255),
    governorate VARCHAR(255),
    lat DOUBLE PRECISION,
    lon DOUBLE PRECISION,
    city_key VARCHAR(255) UNIQUE
);
```

## Configuration

### Application Properties

Key configuration options in `application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/weather-db
spring.datasource.username=postgres
spring.datasource.password=postgres

# API Keys
openweathermap.api.key=your-openweathermap-key
gemini.api.key=your-gemini-api-key

# Flyway Migration
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration

# Oauth Configuration
spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_API_KEY
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_API_SECRET
```

## Usage Examples

### Get Weather Data
```bash
curl http://localhost:8080/weather/cairo
```

### Get AI Recommendations
```bash
curl http://localhost:8080/weather/recommendations/cairo
```

### Add a New City
```bash
curl -X POST http://localhost:8080/cities \
  -H "Content-Type: application/json" \
  -d '{
    "enName": "Cairo",
    "arName": "القاهرة",
    "governorate": "Cairo",
    "lat": 30.0444,
    "lon": 31.2357,
    "cityKey": "cairo"
  }'
```

## Development

### Project Structure
```
src/
├── main/
│   ├── java/com/emara/weather/
│   │   ├── controller/          # REST controllers
│   │   ├── service/             # Business logic
│   │   ├── repository/          # Data access layer
│   │   ├── entity/              # JPA entities
│   │   └── WeatherRecommendationsApplication.java
│   └── resources/
│       ├── application.properties
│       └── db/migration/        # Flyway migration scripts
└── test/                        # Unit and integration tests
```

### Running Tests
```bash
./mvnw test
```

### Building for Production
```bash
./mvnw clean package
java -jar target/weather-recommendations-0.0.1-SNAPSHOT.jar
```

## Docker Support

### Build Docker Image
```bash
docker build -t weather-recommendations .
```

### Run with Docker Compose
```bash
docker-compose up -d
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Environment Variables

For production deployment, use environment variables instead of hardcoded values:

```bash
export OPENWEATHERMAP_API_KEY=your-key
export GEMINI_API_KEY=your-key
export DATABASE_URL=jdbc:postgresql://localhost:5432/weather-db
export DATABASE_USERNAME=postgres
export DATABASE_PASSWORD=postgres
```

## Monitoring and Logging

The application uses Spring Boot's built-in logging. Logs are available in the console and can be configured in `application.properties`:

```properties
logging.level.com.emara.weather=DEBUG
logging.level.org.springframework.web=INFO
```

## Security Considerations

- API keys should be stored as environment variables in production
- Consider implementing rate limiting for API endpoints
- Add authentication and authorization as needed
- Validate and sanitize all user inputs

## Troubleshooting

### Common Issues

1. **Database Connection Issues**
   - Ensure PostgreSQL is running
   - Check database credentials in `application.properties`
   - Verify database name and port

2. **API Key Issues**
   - Verify API keys are correct and active
   - Check API quotas and limits
   - Ensure proper API key configuration

3. **Maven Build Issues**
   - Check Java version (requires Java 21+)
   - Clear Maven cache: `./mvnw clean`
   - Update dependencies: `./mvnw clean install`

## Support

For support and questions, please open an issue in the GitHub repository.
