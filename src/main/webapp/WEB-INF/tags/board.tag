<%@ attribute name="chessBoard" required="false" rtexprvalue="true" type="org.springframework.samples.IdusMartii.model.ChessBoard"
 description="Chessboard to be rendered" %>
<canvas id="canvas" width="${chessBoard.width}" height="${chessBoard.height}"></canvas>
<img id="source" src="resources/images/fondo.jpg" style="display:none">
<img id="HORSE-BLACK" src="resources/images/HORSE-BLACK.png" style="display:none">
<img id="KING-BLACK" src="resources/images/KING-BLACK.png" style="display:none">
<img id="KING-WHITE" src="resources/images/KING-WHITE.png" style="display:none">
<script>
var canvas = document.getElementById("canvas");
var ctx = canvas.getContext("2d");
var image = document.getElementById('source');

ctx.drawImage(image, 0, 0, ${chessBoard.width}, ${chessBoard.height});
</script>
