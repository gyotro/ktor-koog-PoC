package com.ai

import ai.koog.ktor.Koog
import ai.koog.ktor.aiAgent
import ai.koog.prompt.executor.clients.openai.OpenAIModels
import ai.koog.prompt.executor.ollama.client.OllamaClient
import ai.koog.prompt.llm.LLMCapability
import ai.koog.prompt.llm.LLMProvider
import ai.koog.prompt.llm.LLModel
import ai.koog.prompt.llm.OllamaModels
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureFrameworks() {
    install(Koog) {
        llm {
            openAI(apiKey = "your-openai-api-key")
            anthropic(apiKey = "your-anthropic-api-key")
            ollama { baseUrl = "http://localhost:11434" }
            google(apiKey = "your-google-api-key")
            openRouter(apiKey = "your-openrouter-api-key")
            deepSeek(apiKey = "your-deepseek-api-key")
        }
    }
    
    routing {
        route("/ai") {
            post("/chat") {
                val userInput = call.receive<String>()
                val modelName = "llama3.1:latest"
                val llmModel = LLModel(
                    provider = LLMProvider.Ollama,
                    id = modelName,
                    capabilities = listOf(LLMCapability.Completion, LLMCapability.Tools),
                    contextLength = 8192,  // LLaMA 3 standard context length
                    maxOutputTokens = 4096  // Reasonable limit for responses
                )
                val output = aiAgent(userInput, model = llmModel)
                call.respondText(output)
            }
        }
    }
}
