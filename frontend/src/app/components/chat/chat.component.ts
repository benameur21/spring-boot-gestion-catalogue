
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChatService } from '../../services/chat.service';

interface ChatMessage {
  content: string;
  sender: 'user' | 'assistant';
  timestamp: Date;
}

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css'
})
export class ChatComponent {

  messages: ChatMessage[] = [];

  newMessage = '';
  isLoading = false;

  constructor(private chatService: ChatService) {}

  sendMessage(): void {
    const message = this.newMessage.trim();

    if (!message || this.isLoading) {
      return;
    }

    // Display the user's message
    this.messages.push({
      content: message,
      sender: 'user',
      timestamp: new Date()
    });

    this.newMessage = '';
    this.isLoading = true;

    // Send the message to the Spring Boot agent
    this.chatService.sendMessage(message).subscribe({
      next: (response) => {
        this.messages.push({
          content: response,
          sender: 'assistant',
          timestamp: new Date()
        });

        this.isLoading = false;
      },
      error: (error) => {
        console.error('Chat API error:', error);

        this.messages.push({
          content: 'Sorry, I could not connect to the AI assistant. Please try again.',
          sender: 'assistant',
          timestamp: new Date()
        });

        this.isLoading = false;
      }
    });
  }

  sendSuggestion(suggestion: string): void {
    this.newMessage = suggestion;
    this.sendMessage();
  }
}
