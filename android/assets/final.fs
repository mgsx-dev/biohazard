#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform float u_ceil;

void main() {
    vec2 tc = v_texCoords;
    
    vec4 c = texture2D(u_texture, tc);
    
    float rate = c.a > 0.5 ? 0.5 : 1.0;
    
    vec3 cl;
    if(c.b > u_ceil) cl = vec3(1.0, 0.0, 1.0);
    else if(c.g > u_ceil) cl = vec3(1.0, 0.4, 0.2);
    else if(c.r > u_ceil) cl = vec3(1.0, 0.0, 0.0);
    else cl = vec3(0.4, 0.0, 0.0);
    
    if(c.g > 0.2) rate = 1.0;
    
    gl_FragColor = vec4(cl * rate, 1.0);
}
