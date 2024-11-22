# 🌐 Penny Convert

Your one-stop solution for **real-time currency conversions**, **crypto exchanges**, and **market updates** — all with the power of voice commands, insightful facts, and a sleek, user-friendly design!

---

## 🚀 Features

### 💱 Currency Conversion
- Convert between **global currencies** with ease.
- Powered by the **ExchangeRate API** for up-to-date exchange rates.
- **Bonus**: Discover **2 random facts** about the base and target currencies, fetched using the **Gemini API**.

### 💹 Crypto-to-Crypto Conversion
- Trade between your favorite cryptocurrencies.
- **Coinlayer API** ensures reliable and secure crypto data.
- **Bonus**: Learn **2 random facts** about the cryptocurrencies you’re trading with, using the **Gemini API**.

### 🗣️ Voice & Currency Conversion
- Convert currencies or cryptos using voice commands!
- **Speech Recognizer** captures your commands, while **Text-to-Speech** delivers accurate results.
- Enhanced with **Gemini API** for seamless integration.

### 📰 Market News Feed
- Stay informed with the latest **market news** using the **FinHub API**.
- A dedicated section for real-time updates on financial markets.

### 📜 Conversion History
- Keep track of all your conversions!
- History is securely stored in a **Room Database** for offline access.

---

## 🛠️ Tech Stack

### 🔧 Backend APIs:
- **ExchangeRate API**: Currency conversion rates.
- **Coinlayer API**: Crypto-to-crypto conversion.
- **Gemini API**: Voice-integrated currency conversion and random facts generation.
- **FinHub API**: Financial market news.

### 📱 Android Development:
- **Kotlin** & **Java**
- **Room Database** for local data persistence.
- **Speech Recognizer** & **Text-to-Speech** for interactive voice commands.

---

## 📂 Installation

1. Clone this repository:
   ```bash
   git clone https://github.com/yourusername/currency-converter-app.git

2. Open the project in Android Studio.
3. Add your API keys in the local.properties or a dedicated secrets.xml file:
   
         exchangerate_api_key=<YOUR_API_KEY>
         coinlayer_api_key=<YOUR_API_KEY>
         gemini_api_key=<YOUR_API_KEY>
         finhub_api_key=<YOUR_API_KEY>

4. Build and run the app on your Android device.
