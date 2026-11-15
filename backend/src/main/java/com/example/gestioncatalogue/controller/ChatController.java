
package com.example.gestioncatalogue.controller;

import com.example.gestioncatalogue.tools.ProduitTools;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(
            ChatClient.Builder builder,
            ProduitTools produitTools,
            ChatMemory chatMemory) {

        this.chatClient = builder
                // 1. SYSTEM PROMPT: Cadre de confiance et comportement
                .defaultSystem(
                        "Tu es un assistant e-commerce d'elite pour la boutique Polytechnique Sousse.\n" +
                                "Regles strictes que tu dois imperativement respecter :\n" +
                                "- Reponds UNIQUEMENT aux questions relatives aux produits du catalogue.\n" +
                                "- Utilise SYSTEMATIQUEMENT tes outils pour verifier les prix et disponibilites.\n" +
                                "- Si la question concerne un autre sujet, reponds exactement : 'Je ne sais pas'.\n" +
                                "- Ne divulgue jamais de fausses informations inventees."
                )

                // 2. INJECTION DES OUTILS DU CATALOGUE
                .defaultTools(produitTools)

                // 3. MEMOIRE CONVERSATIONNELLE
                .defaultAdvisors(advisors -> advisors
                        .advisors(
                                MessageChatMemoryAdvisor.builder(chatMemory).build()
                        )
                        .param(ChatMemory.CONVERSATION_ID, "default")
                )

                .build();
    }

    @GetMapping("/agent")
    public String askAgent(@RequestParam String message) {
        return this.chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
