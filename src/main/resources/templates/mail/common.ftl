<#macro page style>
    <!doctype html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <style>
            <#switch style>
            <#case "success">
            <@sucessStyle/>
            <#break>

            <#case "warn">
            <@warnStyle/>
            <#break>
            </#switch>
        </style>
    </head>
    <body>
    <#nested/>
    </body>
    </html>
</#macro>

<#macro sucessStyle>

    * {
    margin: 0;
    padding: 0;
    font-size: 100%;
    font-family: 'Avenir Next', "Helvetica Neue", "Helvetica", Helvetica, Arial, sans-serif;
    line-height: 1.65;
    }

    img {
    max-width: 100%;
    margin: 0 auto;
    display: block;
    }

    body,
    .body-wrap {
    width: 100% !important;
    height: 100%;
    background: #efefef;
    -webkit-font-smoothing: antialiased;
    -webkit-text-size-adjust: none;
    }

    a {
    color: #32CD32;
    text-decoration: none;
    }

    .button {
    display: inline-block;
    color: white;
    background: #32CD32;
    border: solid #32CD32;
    border-width: 10px 20px 8px;
    font-weight: bold;
    border-radius: 4px;
    }

    h1, h2, h3, h4, h5, h6 {
    margin-bottom: 20px;
    line-height: 1.25;
    }

    h1 {
    font-size: 32px;
    }

    h2 {
    font-size: 28px;
    }

    h3 {
    font-size: 24px;
    }

    h4 {
    font-size: 20px;
    }

    h5 {
    font-size: 16px;
    }

    p, ul, ol {
    font-size: 16px;
    font-weight: normal;
    margin-bottom: 20px;
    }

    .container {
    display: block !important;
    clear: both !important;
    margin: 0 auto !important;
    max-width: 580px !important;
    }

    .container table {
    width: 100% !important;
    border-collapse: collapse;
    }

    .container .masthead {
    padding: 80px 0;
    background: #32CD32;
    color: white;
    }

    .container .masthead h1 {
    margin: 0 auto !important;
    max-width: 90%;
    text-transform: uppercase;
    }

    .container .content {
    background: white;
    padding: 30px 35px;
    }

    .container .content.footer p {
    margin-bottom: 0;
    color: #888;
    text-align: center;
    font-size: 14px;
    }

    .container .content.footer a {
    color: #888;
    text-decoration: none;
    font-weight: bold;
    }

</#macro>

<#macro warnStyle>

    * {
    margin: 0;
    padding: 0;
    font-size: 100%;
    font-family: 'Avenir Next', "Helvetica Neue", "Helvetica", Helvetica, Arial, sans-serif;
    line-height: 1.65;
    }

    img {
    max-width: 100%;
    margin: 0 auto;
    display: block;
    }

    body,
    .body-wrap {
    width: 100% !important;
    height: 100%;
    background: #efefef;
    -webkit-font-smoothing: antialiased;
    -webkit-text-size-adjust: none;
    }

    a {
    color: #FF6666;
    text-decoration: none;
    }

    .button {
    display: inline-block;
    color: white;
    background: #FF6666;
    border: solid #FF6666;
    border-width: 10px 20px 8px;
    font-weight: bold;
    border-radius: 4px;
    }

    h1, h2, h3, h4, h5, h6 {
    margin-bottom: 20px;
    line-height: 1.25;
    }

    h1 {
    font-size: 32px;
    }

    h2 {
    font-size: 28px;
    }

    h3 {
    font-size: 24px;
    }

    h4 {
    font-size: 20px;
    }

    h5 {
    font-size: 16px;
    }

    p, ul, ol {
    font-size: 16px;
    font-weight: normal;
    margin-bottom: 20px;
    }

    .container {
    display: block !important;
    clear: both !important;
    margin: 0 auto !important;
    max-width: 580px !important;
    }

    .container table {
    width: 100% !important;
    border-collapse: collapse;
    }

    .container .masthead {
    padding: 80px 0;
    background: #FF6666;
    color: white;
    }

    .container .masthead h1 {
    margin: 0 auto !important;
    max-width: 90%;
    text-transform: uppercase;
    }

    .container .content {
    background: white;
    padding: 30px 35px;
    }

    .container .content.footer p {
    margin-bottom: 0;
    color: #888;
    text-align: center;
    font-size: 14px;
    }

    .container .content.footer a {
    color: #888;
    text-decoration: none;
    font-weight: bold;
    }

</#macro>