const API_URL = "/api/rates?usedb=false";

const statusElement = document.getElementById("status");
const ratesBody = document.getElementById("rates-body");
const listView = document.getElementById("list-view");
const detailView = document.getElementById("detail-view");
const detailTitle = document.getElementById("detail-title");
const rateDetail = document.getElementById("rate-detail");
const backButton = document.getElementById("back-button");

async function loadRates() {
    statusElement.textContent = "Načítání kurzů…";

    try {
        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error(`HTTP ${response.status}`);
        }

        const rates = await response.json();
        renderRates(rates);

        statusElement.textContent =
            rates.length === 0 ? "Nejsou k dispozici žádné kurzy." : "";
    } catch (error) {
        console.error("Načítání kurzů selhalo:", error);
        statusElement.textContent =
            "Kurzy se nepodařilo načíst. Zkuste to znovu.";
    }
}

function renderRates(rates) {
    for (const rate of rates) {
        const row = ratesBody.insertRow();
        const currencyButton = document.createElement("button");

        currencyButton.type = "button";
        currencyButton.textContent = rate.shortName;
        currencyButton.addEventListener("click", () => showDetail(rate));

        row.insertCell().append(currencyButton);

        const values = [
            rate.name,
            rate.amount,
            rate.currBuy,
            rate.currSell
        ];

        for (const value of values) {
            row.insertCell().textContent = value ?? "—";
        }
    }
}

function showDetail(rate) {
    detailTitle.textContent = `${rate.shortName} – ${rate.name}`;
    rateDetail.replaceChildren();

    const fields = [
        ["Země", rate.country],
        ["Platnost od", rate.validFrom],
        ["Množství", rate.amount],
        ["Změna", rate.move],
        ["Valuty – nákup", rate.valBuy],
        ["Valuty – prodej", rate.valSell],
        ["Valuty – střed", rate.valMid],
        ["Devizy – nákup", rate.currBuy],
        ["Devizy – prodej", rate.currSell],
        ["Devizy – střed", rate.currMid],
        ["ČNB – střed", rate.cnbMid],
        ["ECB – střed", rate.ecbMid]
    ];

    for (const [label, value] of fields) {
        const term = document.createElement("dt");
        const description = document.createElement("dd");

        term.textContent = label;
        description.textContent = value ?? "—";

        rateDetail.append(term, description);
    }

    listView.hidden = true;
    detailView.hidden = false;
}

function showList() {
    detailView.hidden = true;
    listView.hidden = false;
}

backButton.addEventListener("click", showList);

loadRates();