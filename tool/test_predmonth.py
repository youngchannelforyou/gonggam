import pandas as pd
from sklearn.linear_model import LinearRegression

# 데이터 로드
data = pd.read_csv("dataset/useddata/testdata.csv")


# 입력 변수와 출력 변수 분리
X_수입 = data[data["수입지출여부"] == "수입"]
X_지출 = data[data["수입지출여부"] == "지출"]
X_지출 = X_지출[["월", "사용분야"]]
X_수입 = X_수입[["월", "사용분야"]]
y_수입 = data[data["수입지출여부"] == "수입"]["금액"]
y_지출 = data[data["수입지출여부"] == "지출"]["금액"]

# 사용분야를 one-hot 인코딩
X_수입encoded = pd.get_dummies(X_수입, columns=["사용분야"])
X_지출encoded = pd.get_dummies(X_지출, columns=["사용분야"])

# print(X_수입encoded)
# 모델 학습
model_수입 = LinearRegression()
model_지출 = LinearRegression()

model_수입.fit(X_수입encoded, y_수입)
model_지출.fit(X_지출encoded, y_지출)


# 예측 함수
def predict_monthly_expenses(month, 사용분야):
    if 사용분야 == "MT":
        input_data = pd.DataFrame(
            {
                "월": [month],
                "사용분야_MT": 1,
                "사용분야_쇼핑": 0,
                "사용분야_식사": 0,
                "사용분야_여행": 0,
                "사용분야_체육대회": 0,
            }
        )

    input_data_encoded = input_data

    print(input_data_encoded)
    predicted_수입 = model_수입.predict(input_data_encoded)
    predicted_지출 = model_지출.predict(input_data_encoded)

    return predicted_수입[0], predicted_지출[0]


# 예측 테스트
input_month = 6  # 예측할 월
input_사용분야 = ["MT"]  # 예측할 사용분야
for input in input_사용분야:
    predicted_수입, predicted_지출 = predict_monthly_expenses(input_month, input)

# 예측 결과 출력
print(
    f"예측 결과: 월 {input_month}, 사용분야 {input_사용분야}, 예상 수입: {predicted_수입}, 예상 지출: {predicted_지출}"
)
