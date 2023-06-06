from PIL import Image
from imutils.perspective import four_point_transform
from imutils.contours import sort_contours
import matplotlib.pyplot as plt
import pytesseract
import imutils
import cv2
import re
import requests
import numpy as np


def make_scan_image(image, width, ksize=(5, 5), min_threshold=75, max_threshold=200):
    image_list_title = []
    image_list = []

    org_image = image.copy()
    image = imutils.resize(image, width=width)
    ratio = org_image.shape[1] / float(image.shape[1])

    # 이미지를 grayscale로 변환하고 blur를 적용
    # 모서리를 찾기위한 이미지 연산
    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    blurred = cv2.GaussianBlur(gray, ksize, 0)
    edged = cv2.Canny(blurred, min_threshold, max_threshold)

    image_list_title = ["gray", "blurred", "edged"]
    image_list = [gray, blurred, edged]

    # contours를 찾아 크기순으로 정렬
    cnts = cv2.findContours(edged.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    cnts = imutils.grab_contours(cnts)
    cnts = sorted(cnts, key=cv2.contourArea, reverse=True)

    findCnt = None

    # 정렬된 contours를 반복문으로 수행하며 4개의 꼭지점을 갖는 도형을 검출
    for c in cnts:
        peri = cv2.arcLength(c, True)
        approx = cv2.approxPolyDP(c, 0.02 * peri, True)

        # contours가 크기순으로 정렬되어 있기때문에 제일 첫번째 사각형을 영역으로 판단하고 break
        if len(approx) == 4:
            findCnt = approx
            break

    # 만약 추출한 윤곽이 없을 경우 오류
    if findCnt is None:
        raise Exception(("Could not find outline."))

    output = image.copy()
    cv2.drawContours(output, [findCnt], -1, (0, 255, 0), 2)

    image_list_title.append("Outline")
    image_list.append(output)

    # 원본 이미지에 찾은 윤곽을 기준으로 이미지를 보정
    transform_image = four_point_transform(org_image, findCnt.reshape(4, 2) * ratio)

    return transform_image


def getdata(path):
    img = Image.open(path)
    image_nparray = np.array(img)
    org_image = cv2.cvtColor(image_nparray, cv2.COLOR_RGB2BGR)

    receipt = make_scan_image(
        org_image, width=200, ksize=(5, 5), min_threshold=20, max_threshold=100
    )

    options = "--psm 4"
    text = pytesseract.image_to_string(
        cv2.cvtColor(receipt, cv2.COLOR_BGR2RGB), config=options, lang="kor+eng"
    )

    # OCR결과 출력
    print("[INFO] OCR결과:")
    print("==================")
    print(text)

    return text


if __name__ == "__main__":
    text = getdata("./dataset/img/img3.jpeg")
    print(text)
