document.addEventListener("DOMContentLoaded", () => {
    const headerContainer = document.getElementById("site-header");
    if (!headerContainer) return;
  
    fetch("/pages/header.html")
      .then(res => {
        if (!res.ok) throw new Error("Header not found");
        return res.text();
      })
      .then(html => {
        headerContainer.innerHTML = html;

        initializeTONConnect();
        checkLoginStatus();
        initializeMenu();

      })
      .catch(err => {
        console.error("Не удалось загрузить header или initializeTONConnect", err);
      });
  });

let tonConnectUI;

function initializeTONConnect() {
    tonConnectUI = new TON_CONNECT_UI.TonConnectUI({
        manifestUrl: 'https://dodbo.ru/tonconnect-manifest.json',
        buttonRootId: 'ton-connect-button'
    });

    // 1️⃣ При клике на кнопку “Подключить кошелёк” запрашиваем у бэкенда payload для TON-proof
    const btn = document.getElementById('ton-connect-button');
    btn.addEventListener('click', async () => {
        try {
        // В ПРОДЕ УБРАТЬЬЬЬ!!!!!!!!!!!!!!!!
            tonConnectUI.disconnect()
            // Показываем состояние загрузки
            tonConnectUI.setConnectRequestParameters({ state: 'loading' });

            fetch('/api/ton-proof/payload')
              .then(response => {
                if (!response.ok) {
                  throw new Error(`Ошибка в получении payload: ${response.status}`);
                }
                // читаем тело как строку
                return response.text();
              })
              .then(payload => {
                console.log('Получили payload:', payload);

                // Передаём payload в модалку, чтобы кошелёк подписал его как ton_proof
                tonConnectUI.setConnectRequestParameters({
                    state: 'ready',
                    value: {
                       tonProof: payload
                   }
                });
            });

            await tonConnectUI.openModal();
        } catch (e) {
            console.error('Ошибка при получении TON-proof payload:', e);
            tonConnectUI.setConnectRequestParameters?.(null);
        }
    });

    // 2️⃣ Слушаем изменение статуса: как только придёт подписанный proof — шлём его на проверку
    tonConnectUI.onStatusChange(wallet => {
            console.log(wallet)
            if (wallet && wallet.connectItems?.tonProof && 'proof' in wallet.connectItems.tonProof) {
                  console.log("успех")
                  loginTonProof(wallet.account, wallet.connectItems.tonProof.proof);
            }
        });
}

async function loginTonProof(account, proof) {
  try {
    const response = await fetch('/api/ton-proof/login',
        {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            accountJson: account,
            proofJson: proof
          })
        }
    );

    if (!response.ok) {
      const text = await response.text();
      throw new Error(`Сервер вернул ${response.status}: ${text}`);
    }

    checkLoginStatus();
  } catch (e) {
    console.error('Ошибка при логине:', e);
  }
}

async function checkLoginStatus() {
  try {
    const response = await fetch('/api/ton-proof/status', {
      method: 'GET',
      credentials: 'include'
    });

    if (!response.ok) {
      throw new Error(`Сервер вернул ${response.status}`);
    }

    // распарсим JSON { loggedIn: boolean, address: string | null }
    const data = await response.json();

    console.log('Status response:', data);
    if (data.loggedIn) {
      // пользователь залогинен
      console.log('Адрес из сессии:', data.address);
      // например, показываем его в header:
      document.getElementById('account-img').style.display = "block";
      document.getElementById('ton-connect-button').style.display = "none";
    } else {
      // не залогинен
      console.log('Пользователь не залогинен');
    }

    return data;

  } catch (err) {
    console.error('Не удалось получить статус:', err);
  }
}

function initializeMenu() {
const btn = document.getElementById('mobile-menu-button');
const navWrap = document.querySelector('#nav-container');

btn.addEventListener('click', () => {
  navWrap.classList.toggle('open-menu');
});

navWrap.addEventListener('mouseleave', () => {
  navWrap.classList.remove('open-menu');
});
}
