const searchParams = new URLSearchParams(location.search.substr(1));
const redirectUrl = searchParams.has("redirect")? searchParams.get("redirect") : location.origin;

/**
 * @type {Element[]}
 */
const registerOnlies = document.querySelectorAll(".register-only");
/**
 * @type {Element[]}
 */
const loginOnlies = document.querySelectorAll(".login-only");

let isRegister = false;

function refreshRegister(){
    let onlies = [registerOnlies, loginOnlies];
    if (isRegister) onlies = onlies.reverse();
    onlies[0].forEach(e => e.style.display = "none");
    onlies[1].forEach(e => e.style.display = "inherit");
}

document.querySelectorAll('input[name="register"]').forEach(function(radioButton) {
    radioButton.addEventListener('change', function(event) {
        isRegister = event.target.value === 'true';
        refreshRegister();
    });
});


/**
 * @type {HTMLFormElement}
 */
const form = document.querySelector("#loginForm");
function getAuthHeader(){
    const formData = new FormData(form);
    const rawToken = `${formData.get("username")}:${formData.get("password")}`;
    return `Basic ${btoa(rawToken)}`;
}

form.addEventListener("submit", event => {
    event.preventDefault();

    const formData = new FormData(form);

    /**
     * @type {{surname:string, avatarUrl:string}}
     */
    let params = isRegister ? {
        surname: formData.get("surname"),
        avatarUrl: formData.get("avatarUrl")
    } : {};
    const urlParams = new URLSearchParams();
    urlParams.set("redirect", redirectUrl)
    fetch(isRegister ? `/register?${urlParams}`: `/login?${urlParams}`, {
        redirect: "manual",
        method: form.method,
        body: JSON.stringify(params),
        headers: {
            Accept: '*/*',
            "Content-Type": "application/json",
            Authorization: getAuthHeader()
        }
    }).then(async res => {
        if (res.status !== 200) throw res.body;
        const url = new URL(redirectUrl);
        url.searchParams.set("token",await res.text());
        location.href = url.toString();
    }).catch(reason => console.error(reason));
});

refreshRegister();