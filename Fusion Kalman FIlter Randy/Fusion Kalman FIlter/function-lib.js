			Math.radians = function(degrees) {return degrees * Math.PI / 180;};
			Math.degrees = function(radians) {return radians * 180 / Math.PI;};

			$(document).ready(function() {
				if(isAPIAvailable()) {
					$('#files').bind('change', handleFileSelect);
				}
			});
			function isAPIAvailable() {
				if (window.File && window.FileReader && window.FileList && window.Blob) {
					return true;
				} else {
					document.writeln('The HTML5 APIs used in this form are only available in the following browsers:<br />');
					document.writeln(' - Google Chrome: 13.0 or later<br />');
					document.writeln(' - Mozilla Firefox: 6.0 or later<br />');
					document.writeln(' - Internet Explorer: Not supported (partial support expected in 10.0)<br />');
					document.writeln(' - Safari: Not supported<br />');
					document.writeln(' - Opera: Not supported');
					return false;
				}
			}
			function handleFileSelect(evt) {
				var files = evt.target.files;
				var file = files[0];
				reader = new FileReader();
				reader.readAsText(file);
				reader.onload = function(event){
					var csv = event.target.result;
					sensVals = arrayTranspose($.csv.toArrays(csv));
					addData(sensVals, 0.01, 20001);
				};
				reader.onerror = function(){ 
					alert('Unable to read ' + file.fileName);
				};
			}
			function display(matrix) {
				console.log(matrix.length);
				for(var i=0; i < matrix.length; i++) {
					if(matrix[0].length) {
						var string = "(";
						for (var j=0; j < matrix[0].length; j++) string += ((j==0)?"":", ") + matrix[i][j];
						string += ")";
						console.log(string);
					} else {
						console.log(matrix[i]);
					}
				}
				console.log(" ");
			}
			function arrayTranspose(array) {
				var w = array.length ? array.length : 0, h = array[0] instanceof Array ? array[0].length : 0, i, j, t = [];
				if(h === 0 || w === 0) { return []; }
				for(i=0; i<h; i++) {
					t[i] = [];
					for(j=0; j<w; j++) {
					if(j==0) t[i][j] = array[j][i];
					else t[i][j] = parseFloat(array[j][i]);
					}
				}
				return t;
			}
