shader StandardShader by PBR {
    albedo: texture = "white"
    normal: texture = "default_normal"
    metallic: f32 = 0.5
    roughness: f32 = 0.5

    render_states {
        cull_mode = "back"
        blend_mode = "opaque"
        depth_test = true
        depth_write = true
    }

    vertex(
        @location(0) position: vec3,
        @location(1) normal: vec3,
        @location(2) uv: vec2
    ) -> vec4 {
        let model = uniforms.model
        return projection * view * model * vec4(position, 1.0)
    }

    fragment(
        @location(0) v_normal: vec3,
        @location(1) v_uv: vec2
    ) -> vec4 {
        let albedo = texture_sample(uniforms.albedo, v_uv)
        return vec4(albedo, 1.0)
    }

    uniforms {
        model: mat44
        view: mat44
        projection: mat44
        albedo: tex2
    }

    fallback {
        when: "pbr_not_supported"
        shader: PhongShader
    }
}
