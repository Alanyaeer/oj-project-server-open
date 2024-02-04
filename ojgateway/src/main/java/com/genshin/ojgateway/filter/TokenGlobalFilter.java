package com.genshin.ojgateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2023/12/12 16:23
 */
@Order(0)
@Component
@Slf4j
public class TokenGlobalFilter implements GlobalFilter{
    @Override
    //TODO 添加一些使用的过滤方式
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        List<String> list = headers.get("attack-code");

        if(!CollectionUtils.isEmpty(list)){
            String s = list.get(0);
            if(s.equals("Eren_yeager"))
            return chain.filter(exchange);
        }
        // 拦截
        ServerHttpResponse response = exchange.getResponse();
        response.setRawStatusCode(401);
        return response.setComplete();
    }
}
