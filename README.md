# Ktor Koog AI Agent PoC

A Kotlin/Ktor application demonstrating AI agent capabilities using the Koog framework with Ollama's LLaMA 3.1 model.

## Features

- **AI Agent Integration**: Built with Koog's AI agent framework
- **Tool Integration**: Supports MCP (Model Control Protocol) tools
- **LLM Backend**: Powered by Ollama's LLaMA 3.1 model
- **REST API**: Simple HTTP endpoints for AI interactions

## Prerequisites

- Java 17 or higher
- Gradle 7.6 or higher
- Ollama server running locally (for LLaMA 3.1 model)

## Setup

1. Clone the repository
2. Install Ollama and pull the LLaMA 3.1 model:
   ```bash
   ollama pull llama3.1:latest
   ```
3. Start the Ollama server (if not already running):
   ```bash
   ollama serve
   ```

## API Endpoints

### Chat with AI Agent

```
POST /ai/chat
Content-Type: text/plain

Your message here
```

**Example using cURL:**
```bash
curl -X POST http://localhost:8080/ai/chat \
  -H "Content-Type: text/plain" \
  -d "What's the weather like today?"
```

## Building & Running

### Development

```bash
./gradlew run
```

### Build JAR

```bash
./gradlew buildFatJar
java -jar build/libs/ktor-koog-PoC-all.jar
```

### Docker

Build and run using Docker:

```bash
./gradlew buildImage
docker run -p 8080:8080 ktor-koog-poc
```

## Configuration

The application is configured to connect to a local Ollama server. To change the model or other settings, modify the `Routing.kt` file.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
