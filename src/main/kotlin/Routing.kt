package com.ai

import ai.koog.agents.core.agent.AIAgent
import ai.koog.agents.mcp.McpToolRegistryProvider

import ai.koog.prompt.executor.ollama.client.OllamaClient
import ai.koog.prompt.llm.LLMCapability
import ai.koog.prompt.llm.LLMProvider
import ai.koog.prompt.llm.LLModel
import ai.koog.prompt.message.Message
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ai.koog.prompt.dsl.*


import ai.koog.prompt.dsl.Prompt

import ai.koog.prompt.executor.llms.SingleLLMPromptExecutor

import io.ktor.http.CacheControl
import io.ktor.http.ContentType
import kotlinx.coroutines.flow.collect


fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        route("/ai") {
            post("/chat") {
                val modelName = "llama3.1:latest"
                val systemPrompt = "You are a helpful assistant. Use your weather tool when needed."

                // Get the configured Ollama client
                val llmClient = OllamaClient(baseUrl = "http://localhost:8080")
                val llmModel = LLModel(
                    provider = LLMProvider.Ollama,
                    id = modelName,
                    capabilities = listOf(LLMCapability.Completion, LLMCapability.Tools),
                    contextLength = 8192,
                    maxOutputTokens = 4096
                )
                val executor = SingleLLMPromptExecutor(llmClient)

                // --- MCP tool registry
                val transport = McpToolRegistryProvider.defaultSseTransport("http://localhost:8085")
                val toolRegistry = McpToolRegistryProvider.fromTransport(
                    transport = transport,
                    name = "weather-client",
                    version = "1.0.0"
                )

                // --- Create the agent
                val agent = AIAgent(
                    executor = executor,
                    systemPrompt = systemPrompt,
                    llmModel = llmModel,
                    toolRegistry = toolRegistry,
                    temperature = 0.2
                )

                // --- Read user input
                val userPrompt = call.receive<String>()

                // --- Run with MCP tool orchestration
                val result = agent.run(userPrompt)

                // --- Respond with the final answer
                call.respondText(result.toString())
            }
        }
    }
}