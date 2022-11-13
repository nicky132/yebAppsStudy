package com.xxxx.server.config;

import com.xxxx.server.config.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WbSocket配置类
 * @author harry
 * @since 2022/11/13
 */
@Configuration
@EnableWebSocketMessageBroker
public class WbSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Value("{jwt.tokenHead}")
    private String tokenHead;
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    /**
     * 添加这个Endpoint,这样可以在网页通过websocket连接上服务器
     * 也就是我们配置websocket的服务地址,并且可以指定是否使用websocketJS
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /**
         * 1.将/ws/ep路径注册为端点,用户连接这个端点就可以进行websocket通讯,支持socketJS
         * 2.setAllowedOrigins("*")：允许跨域
         * 3.withSockJS()：支持socketJS访问
         */
        registry.addEndpoint("/ws/ep").setAllowedOrigins("*").withSockJS();
    }
    /**
     * 没用JWT令牌时不用配置
     * 输入通道参数配置
     *
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                MessageHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if(StompCommand.CONNECT.equals(((StompHeaderAccessor) accessor).getCommand())){
                   String token = ((StompHeaderAccessor) accessor).getFirstNativeHeader("Auth-Token");
                   if(!StringUtils.isEmpty(token)){
                       String authToken = token.substring(tokenHead.length());
                       String username = jwtTokenUtil.getUserNameFromToken(token);
                       //token中存在用户名
                       if(!StringUtils.isEmpty(username)){
                           UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                           //验证token是否有效，重新设置用户对象
                           if(jwtTokenUtil.validateToken(authToken,userDetails)){
                               UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                               SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                               ((StompHeaderAccessor) accessor).setUser(authenticationToken);
                           }
                       }
                   }
                }
                return message;
            }
        });
    }

    /**
     * 配置消息代理
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 配置代理域,可以配置多个,配置代理目的地前缀/queue,可以在配置域上向客户端发送消息
        registry.enableSimpleBroker("/queue");
    }
}
