var gulp = require("gulp");
var browserify = require('browserify');
var babelify = require('babelify');
var source = require('vinyl-source-stream');
var glob = require('glob');
var factor = require('factor-bundle');

var entryDirectory = './ui/javascripts';
var outputDirectory = './public/javascripts/target';

gulp.task('build', function () {
    var files = glob.sync(entryDirectory + '/*', {nodir: true});
    var outputs = files.map(function(file){
        return file.replace(entryDirectory, outputDirectory);
    });
    browserify({entries: files}, {debug: true})
        .transform(babelify, {presets: ['react', 'es2015', 'stage-0']})
        .plugin(factor, {
            output: outputs
        })
        .bundle()
        .on('error', function (err) {
            console.log(err.message);
        })
        .pipe(source('bundle.js'))
        .pipe(gulp.dest(outputDirectory));
});

gulp.task('watch', function () {
    gulp.watch(entryDirectory + '/**/*', ['build']);
});

gulp.task('default', ['build', 'watch']);