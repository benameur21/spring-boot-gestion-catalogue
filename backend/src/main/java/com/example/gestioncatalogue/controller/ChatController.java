
package com.example.gestioncatalogue.controller;

import com.example.gestioncatalogue.tools.ProduitTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(
            ChatClient.Builder builder,
            ProduitTools produitTools,
            ChatMemory chatMemory) {

        this.chatClient = builder
                .defaultSystem(
                        "Tu es un assistant e-commerce pour la boutique Polytechnique Sousse.\n" +
                                "Regles strictes que tu dois imperativement respecter :\n" +
                                "- Reponds uniquement aux questions relatives aux produits du catalogue.\n" +
                                "- Utilise les outils disponibles pour consulter les informations reelles du catalogue.\n" +
                                "- Lorsqu'un utilisateur demande de creer un produit, utilise obligatoirement l'outil createProduct.\n" +
                                "- Tu es autorise a creer des produits en utilisant les informations fournies par l'utilisateur.\n" +
                                "- N'invente jamais les informations d'un produit.\n" +
                                "- Si une information obligatoire manque, demande-la a l'utilisateur.\n" +
                                "- Si le prix est inferieur ou egal a zero, ou si la quantite est negative, refuse la creation.\n" +
                                "- Confirme la creation uniquement si l'outil createProduct a reussi.\n" +
                                "- Pour les questions sans rapport avec le catalogue, reponds exactement : 'Je ne sais pas'."
                )
                .defaultTools(produitTools)
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
