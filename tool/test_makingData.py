import pandas as pd
import random
import calendar

# 데이터 개수
num_data = 10000

# 월, 사용분야, 수입지출여부, 금액 데이터 생성
years = list(range(2020, 2024))
months = list(range(1, 13))
사용분야 = ["체육대회", "MT", "쇼핑", "식사", "여행"]
수입지출여부 = ["수입", "지출"]

data = {
    "년도": random.choices(years, k=num_data),
    "월": random.choices(months, k=num_data),
    "일": [],
    "사용분야": random.choices(사용분야, k=num_data),
    "수입지출여부": random.choices(수입지출여부, k=num_data),
    "금액": [random.randint(1000, 10000) for _ in range(num_data)],
}

for i in range(num_data):
    year = data["년도"][i]
    month = data["월"][i]
    _, last_day = calendar.monthrange(year, month)
    data["일"].append(random.randint(1, last_day))

df = pd.DataFrame(data)

# 총금액 계산
df["총금액"] = 0
cumulative_sum = 0

for i, row in df.iterrows():
    if row["수입지출여부"] == "수입":
        cumulative_sum += row["금액"]
    else:
        cumulative_sum -= row["금액"]

    df.at[i, "총금액"] = cumulative_sum

# CSV 파일로 저장
df.to_csv("dataset/useddata/testdata.csv", index=False)
