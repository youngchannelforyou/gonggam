from flask import Flask, request, jsonify
from receipt_recognition import getdata

app = Flask(__name__)


@app.route("/scanimage", methods=["POST"])
def scan_image():
    # 요청에서 이미지 경로를 받아옴
    data = request.get_json()
    image_path = data["path"]

    # 이미지 인식
    text = getdata(image_path)

    # 결과를 JSON 형태로 반환
    response = {"text": text}
    return jsonify(response)


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=7000)
