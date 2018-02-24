#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform vec3 u_size;

void main() {
    vec2 tc = v_texCoords;
    
    float px = u_size.z;
    float dx = px / u_size.x;
    float dy = px / u_size.y;
    
    vec4 t0 = texture2D(u_texture, tc);
    vec4 t1 = texture2D(u_texture, vec2(tc.x - dx, tc.y - dy));
    vec4 t2 = texture2D(u_texture, vec2(tc.x + dx, tc.y - dy));
    vec4 t3 = texture2D(u_texture, vec2(tc.x - dx, tc.y + dy));
    vec4 t4 = texture2D(u_texture, vec2(tc.x + dx, tc.y + dy));

    gl_FragColor = vec4(mix(mix(t1.rgb, t2.rgb, 0.5), mix(t3.rgb, t4.rgb, 0.5), 0.5), t0.a);
}
