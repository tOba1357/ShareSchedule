module.exports = function(grunt) {
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        entry: './main.js',
        watch: {
            browserify: {
                files: ['src/scripts/**/*.js'],
                tasks: ['browserify']
            }
        },

        browserify: {
            dist: {
                options: {
                    // 'es2015'はES2015→ES5への変換、'react'はjsxのコンパイル
                    transform: [['babelify', {presets: ['es2015', 'react']}]]
                },
                // src: ['ui/javascripts/setting.js'],
                src: ['ui/javascripts/registration.js'],
                // dest: 'public/javascripts/target/setting.js',
                dest: 'public/javascripts/target/registration.js',
            }
        }

    });

    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-browserify');

    grunt.registerTask('default', ['browserify']);

};