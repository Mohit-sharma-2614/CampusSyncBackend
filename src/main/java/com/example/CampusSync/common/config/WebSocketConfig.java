package com.example.CampusSync.common.config;

// import java.util.ArrayList;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.messaging.Message;
// import org.springframework.messaging.MessageChannel;
// import org.springframework.messaging.simp.config.ChannelRegistration;
// import org.springframework.messaging.simp.config.MessageBrokerRegistry;
// import org.springframework.messaging.simp.stomp.StompCommand;
// import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
// import org.springframework.messaging.support.ChannelInterceptor;
// import org.springframework.messaging.support.MessageHeaderAccessor;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
// import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
// import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// import com.example.CampusSync.common.security.JWTService;
// import com.example.CampusSync.common.security.MyUserDetailsService;

// @Configuration
// @EnableWebSocketMessageBroker
// public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//     @org.springframework.beans.factory.annotation.Value("${spring.rabbitmq.host}")
//     private String relayHost;

//     @org.springframework.beans.factory.annotation.Value("${app.relay.port}")
//     private int relayPort;

//     @org.springframework.beans.factory.annotation.Value("${spring.rabbitmq.username}")
//     private String clientLogin;

//     @org.springframework.beans.factory.annotation.Value("${spring.rabbitmq.password}")
//     private String clientPasscode;

//     // Inject your JWT Service (Assuming you have one)
//     @Autowired private JWTService jwtService;
//     @Autowired private MyUserDetailsService userDetailsService;

//     @Override
//     public void configureMessageBroker(MessageBrokerRegistry registry) {
//         // Use RabbitMQ as the external broker
//         registry.enableStompBrokerRelay("/topic")
//                 .setRelayHost(relayHost)
//                 .setRelayPort(relayPort)
//                 .setClientLogin(clientLogin)
//                 .setClientPasscode(clientPasscode)
//                 .setSystemLogin(clientLogin)
//                 .setSystemPasscode(clientPasscode);

//         // Prefix for messages sent FROM client TO server (if needed)
//         registry.setApplicationDestinationPrefixes("/app");
//     }

//     @Override
//     public void registerStompEndpoints(StompEndpointRegistry registry) {
//         // The endpoint the Android app connects to
//         registry.addEndpoint("/ws-connect")
//                 .setAllowedOriginPatterns("*"); // Allow Android emulator/device
//                 //.withSockJS(); // Fallback options
//     }

//     // THIS IS THE MISSING AUTHENTICATION PART
//     @Override
//     public void configureClientInboundChannel(ChannelRegistration registration) {
//         registration.interceptors(new ChannelInterceptor() {
//             @Override
//             public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                 StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

//                 if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//                     String authHeader = accessor.getFirstNativeHeader("Authorization");
                    
//                     if (authHeader != null && authHeader.startsWith("Bearer ")) {
//                         String token = authHeader.substring(7);
                        
//                         // VALIDATE TOKEN LOGIC HERE
//                         boolean isValid = jwtService.validateToken(token,userDetailsService.loadUserByUsername(authHeader));
//                         String username = userDetailsService.loadUserByUsername(authHeader).getUsername();

//                         if (isValid) {
//                             // Create generic auth token
//                             UsernamePasswordAuthenticationToken user = 
//                                 new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
//                             accessor.setUser(user);
//                         }
//                     }
//                 }
//                 return message;
//             }
//         });
//     }

// }